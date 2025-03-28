package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
    )
) {
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<EpisodesListViewModel>

    private val currentState: EpisodesListState
        get() = stateWithEffects.value.state

    override fun initialize() {
        sendIntent(EpisodesListIntent.Init(savedStateHandle.getEpisodeFilter()))
    }

    override fun executeIntent(intent: EpisodesListIntent): Flow<Transform<EpisodesListStateWithEffects>> =
        when (intent) {
            is EpisodesListIntent.Init -> flow {
                emit(EpisodesListTransforms.Init(intent.episodeFilter))
                emitAll(loadEpisodes(intent.episodeFilter, instantRefresh = true))
            }

            is EpisodesListIntent.Refresh -> loadEpisodes(
                currentState.episodeFilter,
                instantRefresh = true,
            )

            is EpisodesListIntent.NameChanged -> flow {
                emit(EpisodesListTransforms.ChangeName(name = intent.name))
                val newFilter = currentState.episodeFilter.copy(name = intent.name)
                emitAll(loadEpisodes(newFilter))
            }

            is EpisodesListIntent.SeasonChanged -> flow {
                emit(EpisodesListTransforms.ChangeSeason(season = intent.season))
                val newFilter = currentState.episodeFilter.copy(season = intent.season)
                emitAll(loadEpisodes(newFilter))
            }

            is EpisodesListIntent.EpisodeChanged -> flow {
                emit(EpisodesListTransforms.ChangeEpisode(episode = intent.episode))
                val newFilter = currentState.episodeFilter.copy(episode = intent.episode)
                emitAll(loadEpisodes(newFilter))
            }

            is EpisodesListIntent.EpisodeItemClick -> flowOf(
                EpisodesListTransforms.EpisodeItemClick(intent.episodeId)
            )

            EpisodesListIntent.BackClick -> flowOf(EpisodesListTransforms.BackClick)
        }

    private fun loadEpisodes(
        episodeFilter: EpisodeFilter,
        instantRefresh: Boolean = false,
    ): Flow<Transform<EpisodesListStateWithEffects>> = flow {
        if (!instantRefresh) delay(QUERY_INPUT_DELAY_MILLIS)
        emit(
            EpisodesListTransforms.LoadEpisodesListSuccess(
                loadEpisodesListUseCase(episodeFilter).cachedIn(viewModelScope)
            )
        )
    }
}
