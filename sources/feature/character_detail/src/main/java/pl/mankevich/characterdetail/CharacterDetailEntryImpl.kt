package pl.mankevich.characterdetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.characterdetail.di.CharacterDetailComponent
import pl.mankevich.characterdetail.presentation.CharacterDetailScreen
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterdetailapi.CharacterDetailRoute
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.locationdetailapi.LocationDetailEntry
import javax.inject.Inject

class CharacterDetailEntryImpl @Inject constructor() : CharacterDetailEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry,
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<CharacterDetailViewModel>(
            factory = CharacterDetailComponent.init(dependenciesProvider).getViewModelFactory()
        )

        CharacterDetailScreen(
            viewModel = viewModel,
            onStatusFilterClick = { status ->
                val destination =
                    featureEntries.find<CharactersListEntry>().destination(status = status)
                navController.navigate(destination)
            },
            onSpeciesFilterClick = { species ->
                val destination =
                    featureEntries.find<CharactersListEntry>()
                        .destination(species = species)
                navController.navigate(destination)
            },
            onGenderFilterClick = { gender ->
                val destination =
                    featureEntries.find<CharactersListEntry>().destination(gender = gender)
                navController.navigate(destination)
            },
            onTypeFilterClick = { type ->
                val destination =
                    featureEntries.find<CharactersListEntry>().destination(type = type)
                navController.navigate(destination)
            },
            navigateToLocationDetail = { originId ->
                val destination =
                    featureEntries.find<LocationDetailEntry>().destination(originId)
                navController.navigate(destination)
            },
            navigateToEpisodeDetail = { episodeId ->
                val destination =
                    featureEntries.find<EpisodeDetailEntry>().destination(episodeId)
                navController.navigate(destination)
            },
            navigateBack = { navController.navigateUp() }
        )
    }
}

fun SavedStateHandle.getCharacterId(): Int =
    toRoute<CharacterDetailRoute>().characterId
