package pl.mankevich.rnm.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.navigation.navigateToTopLevelDestination
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.designsystem.component.RnmNavigationSuiteScaffold
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.episodedetailapi.EpisodeDetailEntry
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.locationdetailapi.LocationDetailEntry
import pl.mankevich.locationslistapi.LocationsListEntry

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val featureEntries = LocalDependenciesProvider.current.featureEntries

    val charactersListEntry = featureEntries.find<CharactersListEntry>()
    val characterDetailEntry = featureEntries.find<CharacterDetailEntry>()
    val locationsListEntry = featureEntries.find<LocationsListEntry>()
    val locationsDetailEntry = featureEntries.find<LocationDetailEntry>()
    val episodesListEntry = featureEntries.find<EpisodesListEntry>()
    val episodeDetailEntry = featureEntries.find<EpisodeDetailEntry>()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    RnmNavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = charactersListEntry.featureRoute == currentRoute,
                icon = {
                    Icon(
                        imageVector = RnmIcons.Person,
                        contentDescription = "Characters",
                        modifier = Modifier.size(30.dp)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = RnmIcons.PersonFilled,
                        contentDescription = "Characters",
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text("Characters") },
                onClick = {
                    navController.navigateToTopLevelDestination(charactersListEntry.destination())
                },
            )
            item(
                selected = locationsListEntry.featureRoute == currentRoute,
                icon = {
                    Icon(
                        imageVector = RnmIcons.MapPin,
                        contentDescription = "Locations",
                        modifier = Modifier.size(30.dp)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = RnmIcons.MapPinFilled,
                        contentDescription = "Locations",
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text("Locations") },
                onClick = {
                    navController.navigateToTopLevelDestination(locationsListEntry.destination())
                },
            )
            item(
                selected = episodesListEntry.featureRoute == currentRoute,
                icon = {
                    Icon(
                        imageVector = RnmIcons.MonitorPlay,
                        contentDescription = "Episodes",
                        modifier = Modifier.size(30.dp)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = RnmIcons.MonitorPlayFilled,
                        contentDescription = "Episodes",
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text("Episodes") },
                onClick = {
                    navController.navigateToTopLevelDestination(episodesListEntry.destination())
                },
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = charactersListEntry.destination(),
            modifier = Modifier.fillMaxSize()
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

            with(locationsDetailEntry) {
                composable(navController, featureEntries)
            }

            with(episodesListEntry) {
                composable(navController, featureEntries)
            }

            with(episodeDetailEntry) {
                composable(navController, featureEntries)
            }
        }
    }
}