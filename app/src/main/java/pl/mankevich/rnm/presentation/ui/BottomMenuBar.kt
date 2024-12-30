package pl.mankevich.rnm.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.BlackA
import pl.mankevich.episodeslistapi.EpisodesListEntry
import pl.mankevich.locationslistapi.LocationsListEntry

@Composable
fun BottomMenuBar(
    navController: NavController,
    featureEntries: FeatureEntries
) { //TODO
    BottomNavigationLayout {
        IconButton(
            onClick = {
                val destination = featureEntries.find<CharactersListEntry>().destination()
                navController.navigate(destination)
            }
        ) {
            Icon(
                imageVector = RnmIcons.Person,
                contentDescription = "Characters"
            )
        }

        IconButton(
            onClick = {
                val destination = featureEntries.find<LocationsListEntry>().destination()
                navController.navigate(destination)
            }
        ) {
            Icon(
                imageVector = RnmIcons.MapPin,
                contentDescription = "Locations"
            )
        }

        IconButton(
            onClick = {
                val destination = featureEntries.find<EpisodesListEntry>().destination()
                navController.navigate(destination)
            }
        ) {
            Icon(
                imageVector = RnmIcons.MonitorPlay,
                contentDescription = "Episodes"
            )
        }
    }
}

@Composable
private inline fun BottomNavigationLayout(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Column {
        HorizontalDivider()
        Row(
            modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(BlackA),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}
