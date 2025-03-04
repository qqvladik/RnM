package pl.mankevich.characterdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import pl.mankevich.characterdetail.getCharacterId
import pl.mankevich.coreui.mvi.SideEffects
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
        state = CharacterDetailState(),
        sideEffects = SideEffects<CharacterDetailSideEffect>().add(
            CharacterDetailSideEffect.OnLoadCharacterRequested,
            CharacterDetailSideEffect.OnLoadEpisodesRequested
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<CharacterDetailViewModel>

    val characterId = savedStateHandle.getCharacterId()

    override fun executeIntent(intent: CharacterDetailIntent): Flow<Transform<CharacterDetailStateWithEffects>> =
        when (intent) {
            is CharacterDetailIntent.LoadCharacter -> flowOf(
                CharacterDetailTransforms.LoadCharacter(characterId)
            ).flatMapMerge {
                try {
                    loadCharacterDetailUseCase(characterId = characterId)
                        .map { CharacterDetailTransforms.LoadCharacterSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(CharacterDetailTransforms.LoadCharacterError(e))
                }
            }

            is CharacterDetailIntent.LoadEpisodes -> flowOf(
                CharacterDetailTransforms.LoadEpisodes(characterId)
            ).flatMapMerge {
                try {
                    loadEpisodesByCharacterIdUseCase(characterId = characterId)
                        .map { CharacterDetailTransforms.LoadEpisodesSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(CharacterDetailTransforms.LoadEpisodesError(e))
                }
            }

            is CharacterDetailIntent.EpisodeItemClick -> emptyFlow()
        }

    fun handleSideEffect(
        sideEffect: CharacterDetailSideEffect,
    ) {
        when (sideEffect) {
            is CharacterDetailSideEffect.OnEpisodeItemClicked ->
                sendIntent(CharacterDetailIntent.EpisodeItemClick(sideEffect.episodeId))

            is CharacterDetailSideEffect.OnLoadCharacterRequested ->
                sendIntent(CharacterDetailIntent.LoadCharacter)

            is CharacterDetailSideEffect.OnLoadEpisodesRequested ->
                sendIntent(CharacterDetailIntent.LoadEpisodes)
        }
    }
}
