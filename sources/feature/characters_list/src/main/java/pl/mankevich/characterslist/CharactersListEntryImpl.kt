package pl.mankevich.characterslist

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslist.di.CharactersListComponent
import pl.mankevich.characterslist.presentation.CharactersListScreen
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_GENDER
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_NAME
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_SPECIES
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_STATUS
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_TYPE
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
            onCharacterItemClick = { characterId ->
                val destination = featureEntries.find<CharacterDetailEntry>().destination(characterId)
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

fun SavedStateHandle.getCharacterFilter(): CharacterFilter =
    CharacterFilter(
        name = get<String>(ARG_NAME) ?: "",
        status = get<String>(ARG_STATUS) ?: "",
        species = get<String>(ARG_SPECIES) ?: "",
        type = get<String>(ARG_TYPE) ?: "",
        gender = get<String>(ARG_GENDER) ?: ""
    )