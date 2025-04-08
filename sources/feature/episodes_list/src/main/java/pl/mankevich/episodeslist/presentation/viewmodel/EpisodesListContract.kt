package pl.mankevich.episodeslist.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pl.mankevich.coreui.mvi.MviViewModel
import pl.mankevich.coreui.mvi.StateWithEffects
import pl.mankevich.coreui.mvi.UniqueIntent
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

typealias EpisodesListStateWithEffects = StateWithEffects<EpisodesListState, EpisodesListSideEffect>
typealias EpisodesListMviViewModel = MviViewModel<EpisodesListIntent, EpisodesListStateWithEffects>

sealed interface EpisodesListIntent {

    data class Init(val episodeFilter: EpisodeFilter) : EpisodesListIntent, UniqueIntent

    data object Refresh : EpisodesListIntent, UniqueIntent

    data class NameChanged(val name: String) : EpisodesListIntent, UniqueIntent

    data class EpisodeChanged(val episode: Int?) : EpisodesListIntent, UniqueIntent

    data class SeasonChanged(val season: Int?) : EpisodesListIntent, UniqueIntent

    data class EpisodeItemClick(val episodeId: Int) : EpisodesListIntent

    data object BackClick : EpisodesListIntent
}

sealed interface EpisodesListSideEffect {

    data class NavigateToEpisodeDetail(val episodeId: Int) : EpisodesListSideEffect

    data object NavigateBack : EpisodesListSideEffect
}

@Immutable
data class EpisodesListState(
    val episodeFilter: EpisodeFilter = EpisodeFilter(),
    val isOnline: Boolean = false,
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val episodes: Flow<PagingData<Episode>> = emptyFlow(),
    val seasonLabelList: List<String> = listOf("1", "2", "3", "4", "5"),
    val episodeLabelList: List<String> = emptyList(),
)