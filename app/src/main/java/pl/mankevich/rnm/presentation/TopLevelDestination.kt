package pl.mankevich.rnm.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import pl.mankevich.characterslistapi.CharactersListRoute
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.episodeslistapi.EpisodesListRoute
import pl.mankevich.locationslistapi.LocationsListRoute

@Serializable
data object CharactersTopRoute

@Serializable
data object LocationsTopRoute

@Serializable
data object EpisodesTopRoute

@Serializable
enum class TopLevelDestination(
    val icon: @Composable () -> ImageVector,
    val selectedIcon: @Composable () -> ImageVector,
    val titleText: String,
    val destination: Any,
    val startDestination: Any,
) {
    CHARACTERS(
        icon = { RnmIcons.Person },
        selectedIcon = { RnmIcons.PersonFilled },
        titleText = "Characters",
        destination = CharactersTopRoute,
        startDestination = CharactersListRoute(),
    ),
    LOCATIONS(
        icon = { RnmIcons.MapPin },
        selectedIcon = { RnmIcons.MapPinFilled },
        titleText = "Locations",
        destination = LocationsTopRoute,
        startDestination = LocationsListRoute(),
    ),
    EPISODES(
        icon = { RnmIcons.MonitorPlay },
        selectedIcon = { RnmIcons.MonitorPlayFilled },
        titleText = "Episodes",
        destination = EpisodesTopRoute,
        startDestination = EpisodesListRoute(),
    ),
}
