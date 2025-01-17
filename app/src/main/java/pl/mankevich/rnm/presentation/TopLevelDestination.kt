package pl.mankevich.rnm.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.coreui.navigation.FeatureEntry
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.locationslistapi.LocationsListEntry
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: @Composable () -> ImageVector,
    val titleText: String,
    val route: Class<out FeatureEntry>,
) {
    Characters(
        icon = { RnmIcons.Person },
        titleText = "Characters",
        route = CharactersListEntry::class.java,
    ),
    Locations(
        icon = { RnmIcons.MapPin },
        titleText = "Locations",
        route = LocationsListEntry::class.java,
    ),
    Episodes(
        icon = { RnmIcons.MonitorPlay },
        titleText = "Episodes",
        route = EpisodesListEntry::class.java,
    ),
}