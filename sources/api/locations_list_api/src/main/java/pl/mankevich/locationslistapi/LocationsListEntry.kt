package pl.mankevich.locationslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.core.API_URL
import pl.mankevich.core.LOCATION_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry
import pl.mankevich.locationslistapi.LocationsListEntry.Companion.ARG_DIMENSION
import pl.mankevich.locationslistapi.LocationsListEntry.Companion.ARG_NAME
import pl.mankevich.locationslistapi.LocationsListEntry.Companion.ARG_TYPE

@Serializable
data class LocationsListRoute(
    @SerialName(ARG_NAME)
    val name: String? = null,
    @SerialName(ARG_TYPE)
    val type: String? = null,
    @SerialName(ARG_DIMENSION)
    val dimension: String? = null
)

abstract class LocationsListEntry : TypesafeComposableFeatureEntry<LocationsListRoute>() {

    final override val featureRoute = LocationsListRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(navDeepLink(
        route = featureRoute,
        basePath = "$API_URL/$LOCATION_RELATIVE_PATH"
    ) {
        uriPattern = "$API_URL/$LOCATION_RELATIVE_PATH/" +
                "?$ARG_NAME={$ARG_NAME}" +
                "&$ARG_TYPE={$ARG_TYPE}" +
                "&$ARG_DIMENSION={$ARG_DIMENSION}"
    })

    fun destination(
        name: String? = null,
        type: String? = null,
        dimension: String? = null,
    ) = LocationsListRoute(
        name = name,
        type = type,
        dimension = dimension
    )

    companion object {
        const val ARG_NAME = "name"
        const val ARG_TYPE = "type"
        const val ARG_DIMENSION = "dimension"
    }
}