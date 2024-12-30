package pl.mankevich.rnm.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.coreui.navigation.find
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.locationslistapi.LocationsListEntry
import pl.mankevich.rnm.presentation.ui.BottomMenuBar

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val featureEntries = LocalDependenciesProvider.current.featureEntries

    val charactersListEntry = featureEntries.find<CharactersListEntry>()
    val characterDetailEntry = featureEntries.find<CharacterDetailEntry>()
    val locationsListEntry = featureEntries.find<LocationsListEntry>()
    val episodesListEntry = featureEntries.find<EpisodesListEntry>()

    Column {
        NavHost(
            navController = navController,
            startDestination = charactersListEntry.destination(),
            modifier = Modifier.weight(1f)
        ) {

            with(charactersListEntry) {
                composable(navController, featureEntries)
            }

            with(characterDetailEntry) {
                composable(navController, featureEntries)
            }

            with(locationsListEntry) {
                composable(navController, featureEntries)
            }

            with(episodesListEntry) {
                composable(navController, featureEntries)
            }
        }

        BottomMenuBar(navController, featureEntries)
    }
}