package pl.mankevich.locationdetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.LOCATION_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class LocationDetailEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$LOCATION_RELATIVE_PATH/{$ARG_LOCATION_ID}"

    final override val arguments = listOf(
        navArgument(ARG_LOCATION_ID) {
            type = NavType.IntType
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$API_URL/$LOCATION_RELATIVE_PATH/{$ARG_LOCATION_ID}" })

    fun destination(locationId: Int) =
        "$APP_ROUTE/$LOCATION_RELATIVE_PATH/$locationId"

    companion object {
        const val ARG_LOCATION_ID = "locationId"
    }
}
