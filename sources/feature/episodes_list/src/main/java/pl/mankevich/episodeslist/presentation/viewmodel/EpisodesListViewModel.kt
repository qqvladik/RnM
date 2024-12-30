package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.coreui.mvi.SideEffects
import pl.mankevich.coreui.mvi.Transform
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.episodeslist.getEpisodeFilter
import pl.mankevich.model.EpisodeFilter

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class EpisodesListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
//    private val loadEpisodesListUseCase: LoadEpisodesListUseCase,
) : EpisodesListMviViewModel(
    initialStateWithEffects = EpisodesListStateWithEffects(
        state = EpisodesListState(
            episodeFilter = savedStateHandle.getEpisodeFilter()
        ),
        sideEffects = SideEffects<EpisodesListSideEffect>().add(
            EpisodesListSideEffect.OnRefreshRequested(
                savedStateHandle.getEpisodeFilter()
            )
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EpisodesListViewModel>

    override fun executeIntent(intent: EpisodesListIntent): Flow<Transform<EpisodesListStateWithEffects>> =
        when (intent) {
            is EpisodesListIntent.LoadEpisodes -> loadEpisodes(
                instantRefresh = false,
                intent.episodeFilter
            )

            is EpisodesListIntent.Refresh -> loadEpisodes(
                instantRefresh = true,
                intent.episodeFilter
            )

            is EpisodesListIntent.NameChanged -> flowOf(
                EpisodesListTransforms.ChangeName(name = intent.name)
            )

            is EpisodesListIntent.EpisodeChanged -> flowOf(
                EpisodesListTransforms.ChangeEpisode(episode = intent.episode)
            )

            is EpisodesListIntent.SeasonChanged -> flowOf(
                EpisodesListTransforms.ChangeEpisode(season = intent.season)
            )

            is EpisodesListIntent.CharacterItemClick -> flowOf(
                EpisodesListTransforms.CharacterItemClick(intent.characterId)
            )
        }

    private fun loadEpisodes(
        instantRefresh: Boolean,
        episodeFilter: EpisodeFilter
    ): Flow<Transform<EpisodesListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
//        emit(
//            EpisodesListTransforms.LoadEpisodesListSuccess(
//                loadEpisodesListUseCase(episodeFilter).cachedIn(viewModelScope)
//            )
//        )
    }

    fun handleSideEffect(
        sideEffect: EpisodesListSideEffect,
        onCharacterItemClick: (Int) -> Unit
    ) {
        when (sideEffect) {
            is EpisodesListSideEffect.OnEpisodeItemClicked ->
                onCharacterItemClick(sideEffect.episodeId)

            is EpisodesListSideEffect.OnLoadEpisodesRequested ->
                sendIntent(EpisodesListIntent.LoadEpisodes(sideEffect.episodeFilter))

            is EpisodesListSideEffect.OnRefreshRequested ->
                sendIntent(EpisodesListIntent.Refresh(sideEffect.episodeFilter))
        }
    }
}