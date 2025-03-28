package pl.mankevich.episodedetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.episodedetail.di.EpisodeDetailComponent
import pl.mankevich.episodedetail.presentation.EpisodeDetailScreen
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.episodedetailapi.EpisodeDetailRoute
import pl.mankevich.episodeslistapi.EpisodesListEntry
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
            navigateToEpisodesListByEpisode = { episode ->
                val destination =
                    featureEntries.find<EpisodesListEntry>().destination(episode = episode)
                navController.navigate(destination)
            },
            navigateToEpisodesListBySeason = { season ->
                val destination =
                    featureEntries.find<EpisodesListEntry>().destination(season = season)
                navController.navigate(destination)
            },
            navigateToCharacterDetail = { characterId ->
                val destination =
                    featureEntries.find<CharacterDetailEntry>().destination(characterId)
                navController.navigate(destination)
            },
            navigateUp = { navController.navigateUp() }
        )
    }
}

fun SavedStateHandle.getEpisodeId(): Int =
    toRoute<EpisodeDetailRoute>().episodeId