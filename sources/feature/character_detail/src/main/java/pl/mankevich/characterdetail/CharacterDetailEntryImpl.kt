package pl.mankevich.characterdetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.characterdetail.di.CharacterDetailComponent
import pl.mankevich.characterdetail.presentation.CharacterDetailScreen
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.core.navigation.FeatureEntries
import pl.mankevich.core.navigation.find
import pl.mankevich.core.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import javax.inject.Inject

class CharacterDetailEntryImpl @Inject constructor() : CharacterDetailEntry() {

    @Composable
    override fun Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<CharacterDetailViewModel>(
            factory = CharacterDetailComponent.init(dependenciesProvider).getViewModelFactory()
        )

        CharacterDetailScreen(
            viewModel = viewModel,
            onStatusFilterClick = { status ->
                val destination = featureEntries.find<CharactersListEntry>().destination(status = status)
                navController.navigate(destination)
            },
            onSpeciesFilterClick = { species ->
                val destination = featureEntries.find<CharactersListEntry>().destination(species = species)
                navController.navigate(destination)
            },
            onGenderFilterClick = { gender ->
                val destination = featureEntries.find<CharactersListEntry>().destination(gender = gender)
                navController.navigate(destination)
            },
            onTypeFilterClick = { type ->
                val destination = featureEntries.find<CharactersListEntry>().destination(type = type)
                navController.navigate(destination)
            },
            onBackPress = { navController.popBackStack() }
        )
    }
}

fun SavedStateHandle.getCharacterId(): Int =
    get<Int>(CharacterDetailEntry.ARG_CHARACTER_ID)!!