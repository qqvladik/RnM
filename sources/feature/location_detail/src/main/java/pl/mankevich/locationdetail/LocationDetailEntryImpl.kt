package pl.mankevich.locationdetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.locationdetail.di.LocationDetailComponent
import pl.mankevich.locationdetail.presentation.LocationDetailScreen
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailViewModel
import pl.mankevich.locationdetailapi.LocationDetailEntry
import pl.mankevich.locationdetailapi.LocationDetailRoute
import pl.mankevich.locationslistapi.LocationsListEntry
import javax.inject.Inject

class LocationDetailEntryImpl @Inject constructor() : LocationDetailEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry,
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<LocationDetailViewModel>(
            factory = LocationDetailComponent.init(dependenciesProvider).getViewModelFactory()
        )

        LocationDetailScreen(
            viewModel = viewModel,
            onTypeFilterClick = { type ->
                val destination =
                    featureEntries.find<LocationsListEntry>().destination(type = type)
                navController.navigate(destination)
            },
            onDimensionFilterClick = { dimension ->
                val destination =
                    featureEntries.find<LocationsListEntry>()
                        .destination(dimension = dimension)
                navController.navigate(destination)
            },
            onBackPress = { navController.navigateUp() }
        )
    }
}

fun SavedStateHandle.getLocationId(): Int =
    toRoute<LocationDetailRoute>().locationId
