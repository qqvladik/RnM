package pl.mankevich.episodeslist

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.core.util.extractEpisode
import pl.mankevich.core.util.extractSeason
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.episodeslist.di.EpisodesListComponent
import pl.mankevich.episodeslist.presentation.EpisodesListScreen
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.episodeslistapi.EpisodesListRoute
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
                val destination =
                    featureEntries.find<EpisodeDetailEntry>().destination(episodeId)
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

fun SavedStateHandle.getEpisodeFilter(): EpisodeFilter =
    toRoute<EpisodesListRoute>().toEpisodeFilter()

fun EpisodesListRoute.toEpisodeFilter() =
    EpisodeFilter(
        name = name ?: "",
        season = episode.extractSeason(),
        episode = episode.extractEpisode(),
    )
