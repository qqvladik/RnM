package pl.mankevich.characterdetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterdetail.di.CharacterDetailComponent
import pl.mankevich.characterdetail.presentation.CharacterDetailScreen
import pl.mankevich.core.navigation.FeatureEntries
import pl.mankevich.core.util.injectedViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import javax.inject.Inject

class CharacterDetailEntryImpl @Inject constructor() : CharacterDetailEntry() {

    @Composable
    override fun Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val characterId = backStackEntry.arguments?.getInt(ARG_CHARACTER_ID)!!
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = injectedViewModel {
            CharacterDetailComponent.init(dependenciesProvider).getViewModel()
        }
        CharacterDetailScreen(characterId, viewModel)
    }
}