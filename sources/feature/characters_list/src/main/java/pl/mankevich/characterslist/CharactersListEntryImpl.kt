package pl.mankevich.characterslist

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslist.di.CharactersListComponent
import pl.mankevich.characterslist.presentation.CharactersListScreen
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.core.navigation.FeatureEntries
import pl.mankevich.core.navigation.find
import pl.mankevich.core.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import javax.inject.Inject

class CharactersListEntryImpl @Inject constructor() : CharactersListEntry() {

    @Composable
    override fun Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<CharactersListViewModel>(
            factory = CharactersListComponent.init(dependenciesProvider).getViewModelFactory()
        )
        CharactersListScreen(viewModel) { characterId ->
            val destination = featureEntries.find<CharacterDetailEntry>().destination(characterId)
            navController.navigate(destination)
        }
    }
}