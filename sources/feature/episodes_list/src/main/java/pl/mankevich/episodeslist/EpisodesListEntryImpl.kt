package pl.mankevich.episodeslist

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.episodeslist.di.EpisodesListComponent
import pl.mankevich.episodeslist.presentation.EpisodesListScreen
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.episodeslistapi.EpisodesListEntry.Companion.ARG_EPISODE
import pl.mankevich.episodeslistapi.EpisodesListEntry.Companion.ARG_NAME
import pl.mankevich.episodeslistapi.EpisodesListEntry.Companion.ARG_SEASON
import pl.mankevich.model.EpisodeFilter
import javax.inject.Inject

class EpisodesListEntryImpl @Inject constructor() : EpisodesListEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<EpisodesListViewModel>(
            factory = EpisodesListComponent.init(dependenciesProvider).getViewModelFactory()
        )
        EpisodesListScreen(
            viewModel = viewModel,
            onEpisodeItemClick = { episodeId ->
                val destination = featureEntries.find<EpisodeDetailEntry>().destination(episodeId)
                navController.navigate(destination)
            },
            onBackPress = if (navController.previousBackStackEntry != null) {
                { navController.popBackStack() }
            } else {
                null
            }
        )
    }
}

fun SavedStateHandle.getEpisodeFilter(): EpisodeFilter {
    val season = get<Int>(ARG_SEASON)
    val episode = get<Int>(ARG_EPISODE)
    return EpisodeFilter(
        name = get<String>(ARG_NAME) ?: "",
        season = if (season == -1) null else season,
        episode = if (episode == -1) null else episode,
    )
}
