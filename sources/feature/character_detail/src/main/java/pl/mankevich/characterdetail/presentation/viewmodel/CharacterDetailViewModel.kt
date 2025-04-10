package pl.mankevich.characterdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import pl.mankevich.characterdetail.getCharacterId
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase

class CharacterDetailViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadCharacterDetailUseCase: LoadCharacterDetailUseCase,
    private val loadEpisodesByCharacterIdUseCase: LoadEpisodesByCharacterIdUseCase,
) : CharacterDetailMviViewModel(
    initialStateWithEffects = CharacterDetailStateWithEffects(
        state = CharacterDetailState()
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharacterDetailViewModel>

    val characterId = savedStateHandle.getCharacterId()

    override fun initialize() {
        sendIntent(CharacterDetailIntent.LoadCharacterDetail)
    }

    override fun executeIntent(intent: CharacterDetailIntent): Flow<Transform<CharacterDetailStateWithEffects>> =
        when (intent) {
            CharacterDetailIntent.LoadCharacterDetail ->
                loadCharacter().onEach { characterDetailTransform ->
                    if (
                        characterDetailTransform is CharacterDetailTransforms.LoadCharacterSuccess
                        && characterDetailTransform.isOnline != null
                    ) {
                        sendIntent(CharacterDetailIntent.LoadEpisodes)
                    }
                }

            CharacterDetailIntent.LoadEpisodes -> loadEpisodes()

            is CharacterDetailIntent.StatusFilterClick -> flowOf(
                CharacterDetailTransforms.StatusFilterClick(intent.status)
            )

            is CharacterDetailIntent.SpeciesFilterClick -> flowOf(
                CharacterDetailTransforms.SpeciesFilterClick(intent.species)
            )

            is CharacterDetailIntent.GenderFilterClick -> flowOf(
                CharacterDetailTransforms.GenderFilterClick(intent.gender)
            )

            is CharacterDetailIntent.TypeFilterClick -> flowOf(
                CharacterDetailTransforms.TypeFilterClick(intent.type)
            )

            is CharacterDetailIntent.LocationItemClick -> flowOf(
                CharacterDetailTransforms.LocationItemClick(intent.locationId)
            )

            is CharacterDetailIntent.EpisodeItemClick -> flowOf(
                CharacterDetailTransforms.EpisodeItemClick(intent.episodeId)
            )

            CharacterDetailIntent.BackClick -> flowOf(
                CharacterDetailTransforms.BackClick
            )
        }

    private fun loadCharacter(): Flow<Transform<CharacterDetailStateWithEffects>> =
        flow {
            emit(CharacterDetailTransforms.LoadCharacter(characterId))

            // Workaround to give some time for indicator from pullToRefresh to consume all states
            // during recompositions. Because otherwise pullToRefresh indicator doesn't hide
            delay(20)

            val result = loadCharacterDetailUseCase(characterId = characterId)
                .map {
                    CharacterDetailTransforms.LoadCharacterSuccess(
                        isOnline = it.isOnline,
                        character = it.character
                    )
                }
                .catch {
                    emit(CharacterDetailTransforms.LoadCharacterError(it))
                }
            emitAll(result)
        }

    private fun loadEpisodes(): Flow<Transform<CharacterDetailStateWithEffects>> =
        flow {
            emit(CharacterDetailTransforms.LoadEpisodes(characterId))
            val result = loadEpisodesByCharacterIdUseCase(characterId = characterId)
                .map { CharacterDetailTransforms.LoadEpisodesSuccess(it) }
                .catch {
                    emit(CharacterDetailTransforms.LoadEpisodesError(it))
                }
            emitAll(result)
        }
}
