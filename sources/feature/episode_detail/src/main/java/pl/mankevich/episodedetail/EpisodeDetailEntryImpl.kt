package pl.mankevich.episodedetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.episodedetail.di.EpisodeDetailComponent
import pl.mankevich.episodedetail.presentation.EpisodeDetailScreen
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import javax.inject.Inject

class EpisodeDetailEntryImpl @Inject constructor() : EpisodeDetailEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry,
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<EpisodeDetailViewModel>(
            factory = EpisodeDetailComponent.init(dependenciesProvider).getViewModelFactory()
        )

        EpisodeDetailScreen(
            viewModel = viewModel,
            onEpisodeFilterClick = { episode ->
                val destination = featureEntries.find<EpisodesListEntry>().destination(episode = episode)
                navController.navigate(destination)
            },
            onSeasonFilterClick = { season ->
                val destination = featureEntries.find<EpisodesListEntry>().destination(season = season)
                navController.navigate(destination)
            },
            onBackPress = { navController.popBackStack() }
        )
    }
}

fun SavedStateHandle.getEpisodeId(): Int =
    get<Int>(EpisodeDetailEntry.ARG_EPISODE_ID)!!