package pl.mankevich.characterslist

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslist.di.CharactersListComponent
import pl.mankevich.characterslist.presentation.CharactersListScreen
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.characterslistapi.CharactersListRoute
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.model.CharacterFilter
import javax.inject.Inject

class CharactersListEntryImpl @Inject constructor() : CharactersListEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<CharactersListViewModel>(
            factory = CharactersListComponent.init(dependenciesProvider).getViewModelFactory()
        )
        CharactersListScreen(
            viewModel = viewModel,
            navigateToCharacterDetail = { characterId ->
                val destination =
                    featureEntries.find<CharacterDetailEntry>().destination(characterId)
                navController.navigate(destination)
            },
            navigateBack = navController.previousBackStackEntry?.let {
                { navController.popBackStack() }
            },
        )
    }
}

fun SavedStateHandle.getCharacterFilterTypesafe(): CharacterFilter =
    this.toRoute<CharactersListRoute>().toCharacterFilter()

fun CharactersListRoute.toCharacterFilter() = CharacterFilter(
    name = name ?: "",
    status = status ?: "",
    species = species ?: "",
    type = type ?: "",
    gender = gender ?: ""
)
