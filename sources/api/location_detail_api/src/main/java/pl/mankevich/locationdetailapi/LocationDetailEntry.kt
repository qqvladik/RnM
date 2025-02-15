package pl.mankevich.locationdetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.core.API_URL
import pl.mankevich.core.LOCATION_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry
import pl.mankevich.locationdetailapi.LocationDetailEntry.Companion.ARG_LOCATION_ID

@Serializable
data class LocationDetailRoute(
    @SerialName(ARG_LOCATION_ID)
    val locationId: Int
)

abstract class LocationDetailEntry : TypesafeComposableFeatureEntry<LocationDetailRoute>() {

    final override val featureRoute = LocationDetailRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(navDeepLink(
        route = featureRoute,
        basePath = "$API_URL/$LOCATION_RELATIVE_PATH"
    ) {
        uriPattern = "$API_URL/$LOCATION_RELATIVE_PATH/{$ARG_LOCATION_ID}"
    })

    fun destination(locationId: Int) = LocationDetailRoute(locationId)

    companion object {
        const val ARG_LOCATION_ID = "locationId"
    }
}
