package pl.mankevich.characterdetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import pl.mankevich.characterdetail.getCharacterId
import pl.mankevich.core.mvi.SideEffects
import pl.mankevich.core.mvi.Transform
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun executeIntent(intent: CharacterDetailIntent): Flow<Transform<CharacterDetailStateWithEffects>> =
        when (intent) {
            is CharacterDetailIntent.LoadCharacter -> flowOf(
                CharacterDetailTransforms.LoadCharacter(savedStateHandle.getCharacterId())
            )
                .flatMapMerge {
                    loadCharacterDetailUseCase(characterId = savedStateHandle.getCharacterId())
                        .map { CharacterDetailTransforms.LoadCharacterSuccess(it) }
                }

            is CharacterDetailIntent.LoadEpisodes -> flowOf(
                CharacterDetailTransforms.LoadEpisodes(savedStateHandle.getCharacterId())
            ).flatMapMerge {
                loadEpisodesByCharacterIdUseCase(characterId = savedStateHandle.getCharacterId())
                    .map { CharacterDetailTransforms.LoadEpisodesSuccess(it) }
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
