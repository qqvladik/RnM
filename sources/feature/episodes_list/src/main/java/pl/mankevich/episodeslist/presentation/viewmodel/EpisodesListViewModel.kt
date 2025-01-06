package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
import pl.mankevich.domainapi.usecase.LoadEpisodesListUseCase
import pl.mankevich.episodeslist.getEpisodeFilter
import pl.mankevich.model.EpisodeFilter

private const val QUERY_INPUT_DELAY_MILLIS = 500L

class EpisodesListViewModel
@AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadEpisodesListUseCase: LoadEpisodesListUseCase,
) : EpisodesListMviViewModel(
    initialStateWithEffects = EpisodesListStateWithEffects(
        state = EpisodesListState(),
        sideEffects = SideEffects<EpisodesListSideEffect>().add(
            EpisodesListSideEffect.OnInitRequested(
                savedStateHandle.getEpisodeFilter()
            )
        )
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EpisodesListViewModel>

    override fun executeIntent(intent: EpisodesListIntent): Flow<Transform<EpisodesListStateWithEffects>> =
        when (intent) {
            is EpisodesListIntent.Init -> flowOf(
                EpisodesListTransforms.Init(intent.episodeFilter)
            )

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

            is EpisodesListIntent.SeasonChanged -> flowOf(
                EpisodesListTransforms.ChangeSeason(season = intent.season)
            )

            is EpisodesListIntent.EpisodeChanged -> flowOf(
                EpisodesListTransforms.ChangeEpisode(episode = intent.episode)
            )

            is EpisodesListIntent.EpisodeItemClick -> flowOf(
                EpisodesListTransforms.EpisodeItemClick(intent.characterId)
            )
        }

    private fun loadEpisodes(
        instantRefresh: Boolean,
        episodeFilter: EpisodeFilter
    ): Flow<Transform<EpisodesListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            EpisodesListTransforms.LoadEpisodesListSuccess(
                loadEpisodesListUseCase(episodeFilter).cachedIn(viewModelScope)
            )
        )
    }

    fun handleSideEffect(
        sideEffect: EpisodesListSideEffect,
        onCharacterItemClick: (Int) -> Unit
    ) {
        when (sideEffect) {
            is EpisodesListSideEffect.OnInitRequested ->
                sendIntent(EpisodesListIntent.Init(sideEffect.episodeFilter))

            is EpisodesListSideEffect.OnEpisodeItemClicked ->
                onCharacterItemClick(sideEffect.episodeId)

            is EpisodesListSideEffect.OnLoadEpisodesRequested ->
                sendIntent(EpisodesListIntent.LoadEpisodes(sideEffect.episodeFilter))

            is EpisodesListSideEffect.OnRefreshRequested ->
                sendIntent(EpisodesListIntent.Refresh(sideEffect.episodeFilter))
        }
    }
}