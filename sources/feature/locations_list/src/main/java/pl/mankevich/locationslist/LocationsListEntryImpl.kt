package pl.mankevich.locationslist

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.find
import pl.mankevich.coreui.viewmodel.daggerViewModel
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.locationdetailapi.LocationDetailEntry
import pl.mankevich.locationslist.di.LocationsListComponent
import pl.mankevich.locationslist.presentation.LocationsListScreen
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListViewModel
import pl.mankevich.locationslistapi.LocationsListEntry
import pl.mankevich.locationslistapi.LocationsListRoute
import pl.mankevich.model.LocationFilter
import javax.inject.Inject

class LocationsListEntryImpl @Inject constructor() : LocationsListEntry() {

    @Composable
    override fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        val dependenciesProvider = LocalDependenciesProvider.current
        val viewModel = daggerViewModel<LocationsListViewModel>(
            factory = LocationsListComponent.init(dependenciesProvider).getViewModelFactory()
        )
        LocationsListScreen(
            viewModel = viewModel,
            navigateToLocationDetail = { locationId ->
                val destination =
                    featureEntries.find<LocationDetailEntry>().destination(locationId)
                navController.navigate(destination)
            },
            navigateBack = navController.previousBackStackEntry?.let {
                { navController.popBackStack() }
            },
        )
    }
}

fun SavedStateHandle.getLocationFilter(): LocationFilter =
    toRoute<LocationsListRoute>().toLocationFilter()

fun LocationsListRoute.toLocationFilter() =
    LocationFilter(
        name = name ?: "",
        type = type ?: "",
        dimension = dimension ?: ""
    )