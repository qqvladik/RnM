package pl.mankevich.episodedetail.presentation.viewmodel

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
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.domainapi.usecase.LoadCharactersByEpisodeIdUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodeDetailUseCase
import pl.mankevich.episodedetail.getEpisodeId

class EpisodeDetailViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadEpisodeDetailUseCase: LoadEpisodeDetailUseCase,
    private val loadCharactersByEpisodeIdUseCase: LoadCharactersByEpisodeIdUseCase,
) : EpisodeDetailMviViewModel(
    initialStateWithEffects = EpisodeDetailStateWithEffects(
        state = EpisodeDetailState(),
    )
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EpisodeDetailViewModel>

    val episodeId = savedStateHandle.getEpisodeId()

    override fun initialize() {
        sendIntent(EpisodeDetailIntent.LoadEpisodeDetail)
    }

    override fun executeIntent(intent: EpisodeDetailIntent): Flow<Transform<EpisodeDetailStateWithEffects>> =
        when (intent) {
            is EpisodeDetailIntent.LoadEpisodeDetail ->
                loadEpisode().onEach { characterDetailTransform ->
                    if (
                        characterDetailTransform is EpisodeDetailTransforms.LoadEpisodeSuccess
                        && characterDetailTransform.isOnline != null
                    ) {
                        sendIntent(EpisodeDetailIntent.LoadCharacters)
                    }
                }

            is EpisodeDetailIntent.LoadCharacters -> loadCharacters()

            is EpisodeDetailIntent.SeasonFilterClick -> flowOf(
                EpisodeDetailTransforms.SeasonFilterClick(intent.season)
            )

            is EpisodeDetailIntent.EpisodeFilterClick -> flowOf(
                EpisodeDetailTransforms.EpisodeFilterClick(intent.episode)
            )

            is EpisodeDetailIntent.CharacterItemClick -> flowOf(
                EpisodeDetailTransforms.CharacterItemClick(intent.characterId)
            )

            is EpisodeDetailIntent.BackClick -> flowOf(
                EpisodeDetailTransforms.BackClick
            )

        }


    private fun loadEpisode(): Flow<Transform<EpisodeDetailStateWithEffects>> =
        flow {
            emit(EpisodeDetailTransforms.LoadEpisode(episodeId))

            // Workaround to give some time for indicator from pullToRefresh to consume all states
            // during recompositions. Because otherwise pullToRefresh indicator doesn't hide
            delay(20)

            val result = loadEpisodeDetailUseCase(episodeId = episodeId)
                .map {
                    EpisodeDetailTransforms.LoadEpisodeSuccess(
                        isOnline = it.isOnline,
                        episode = it.episode
                    )
                }
                .catch {
                    emit(EpisodeDetailTransforms.LoadEpisodeError(it))
                }
            emitAll(result)
        }

    private fun loadCharacters(): Flow<Transform<EpisodeDetailStateWithEffects>> =
        flow {
            emit(EpisodeDetailTransforms.LoadCharacters(episodeId))
            val result = loadCharactersByEpisodeIdUseCase(episodeId = episodeId)
                .map { EpisodeDetailTransforms.LoadCharactersSuccess(it) }
                .catch {
                    emit(EpisodeDetailTransforms.LoadCharacterError(it))
                }
            emitAll(result)
        }
}
