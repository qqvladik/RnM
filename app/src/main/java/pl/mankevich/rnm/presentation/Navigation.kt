package pl.mankevich.rnm.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.core.navigation.find
import pl.mankevich.dependencies.LocalDependenciesProvider

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val featureEntries = LocalDependenciesProvider.current.featureEntries

    val charactersListEntry = featureEntries.find<CharactersListEntry>()
    val characterDetailEntry = featureEntries.find<CharacterDetailEntry>()

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = charactersListEntry.destination()) {

            with(charactersListEntry) {
                composable(navController, featureEntries)
            }

            with(characterDetailEntry) {
                composable(navController, featureEntries)
            }
        }
    }

//    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
//        BottomMenuBar(navController, destinations) //TODO
//    }
}