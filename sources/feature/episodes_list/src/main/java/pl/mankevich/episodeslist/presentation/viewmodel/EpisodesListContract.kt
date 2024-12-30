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

sealed class EpisodesListIntent {

    data class LoadEpisodes(val episodeFilter: EpisodeFilter) : EpisodesListIntent(), UniqueIntent

    data class Refresh(val episodeFilter: EpisodeFilter) : EpisodesListIntent(), UniqueIntent

    data class CharacterItemClick(val characterId: Int) : EpisodesListIntent()

    data class NameChanged(val name: String) : EpisodesListIntent()

    data class EpisodeChanged(val episode: String) : EpisodesListIntent()

    data class SeasonChanged(val season: String) : EpisodesListIntent()
}

sealed interface EpisodesListSideEffect {

    data class OnEpisodeItemClicked(val episodeId: Int) : EpisodesListSideEffect

    data class OnLoadEpisodesRequested(val episodeFilter: EpisodeFilter) :
        EpisodesListSideEffect

    data class OnRefreshRequested(val episodeFilter: EpisodeFilter) : EpisodesListSideEffect
}

@Immutable
data class EpisodesListState(
    val episodeFilter: EpisodeFilter = EpisodeFilter(),
    // Flow is unstable, so it will always recompose https://issuetracker.google.com/issues/183495984
    // Currently there is no solution how to fit Paging in Unidirectional data flow (MVI)
    val episodes: Flow<PagingData<Episode>> = emptyFlow(),
    val episodeLabelList: List<String> = emptyList(),
    val seasonLabelList: List<String> = listOf("1", "2", "3", "4", "5"),
)