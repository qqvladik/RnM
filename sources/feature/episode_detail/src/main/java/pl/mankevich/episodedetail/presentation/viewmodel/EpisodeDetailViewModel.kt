package pl.mankevich.episodedetail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import pl.mankevich.coreui.mvi.SideEffects
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
        sideEffects = SideEffects<EpisodeDetailSideEffect>().add(
            EpisodeDetailSideEffect.OnLoadEpisodeRequested,
            EpisodeDetailSideEffect.OnLoadEpisodesRequested
        )
    )
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EpisodeDetailViewModel>

    val episodeId = savedStateHandle.getEpisodeId()

    override fun executeIntent(intent: EpisodeDetailIntent): Flow<Transform<EpisodeDetailStateWithEffects>> =
        when (intent) {
            is EpisodeDetailIntent.LoadEpisode -> flowOf(
                EpisodeDetailTransforms.LoadEpisode(episodeId)
            ).flatMapMerge {
                try {
                    loadEpisodeDetailUseCase(episodeId = episodeId)
                        .map { EpisodeDetailTransforms.LoadEpisodeSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(EpisodeDetailTransforms.LoadEpisodeError(e))
                }
            }

            is EpisodeDetailIntent.LoadCharacters -> flowOf(
                EpisodeDetailTransforms.LoadCharacters(episodeId)
            ).flatMapMerge {
                try {
                    loadCharactersByEpisodeIdUseCase(episodeId = episodeId)
                        .map { EpisodeDetailTransforms.LoadCharactersSuccess(it) }
                } catch (e: Throwable) {
                    flowOf(EpisodeDetailTransforms.LoadCharacterError(e))
                }
            }

            is EpisodeDetailIntent.CharacterItemClick -> emptyFlow()
        }

    fun handleSideEffect(
        sideEffect: EpisodeDetailSideEffect,
    ) {
        when (sideEffect) {
            is EpisodeDetailSideEffect.OnCharacterItemClicked ->
                sendIntent(EpisodeDetailIntent.CharacterItemClick(sideEffect.characterId))

            is EpisodeDetailSideEffect.OnLoadEpisodeRequested ->
                sendIntent(EpisodeDetailIntent.LoadEpisode)

            is EpisodeDetailSideEffect.OnLoadEpisodesRequested ->
                sendIntent(EpisodeDetailIntent.LoadCharacters)
        }
    }
}
