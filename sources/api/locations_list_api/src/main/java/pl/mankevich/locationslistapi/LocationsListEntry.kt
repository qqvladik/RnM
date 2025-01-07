package pl.mankevich.locationslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.LOCATION_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class LocationsListEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$LOCATION_RELATIVE_PATH/" +
            "?$ARG_NAME={$ARG_NAME}" +
            "&$ARG_TYPE={$ARG_TYPE}" +
            "&$ARG_DIMENSION={$ARG_DIMENSION}"

    final override val arguments = listOf(
        navArgument(ARG_NAME) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_TYPE) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_DIMENSION) {
            type = NavType.StringType
            nullable = true
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink {
            uriPattern = "$API_URL/$LOCATION_RELATIVE_PATH/" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_TYPE={$ARG_TYPE}" +
                    "&$ARG_DIMENSION={$ARG_DIMENSION}"
        })

    fun destination(
        name: String? = null,
        type: String? = null,
        dimension: String? = null,
    ) = "$APP_ROUTE/$LOCATION_RELATIVE_PATH/" +
            "?$ARG_NAME=$name" +
            "&$ARG_TYPE=$type" +
            "&$ARG_DIMENSION=$dimension"

    companion object {
        const val ARG_NAME = "name"
        const val ARG_TYPE = "type"
        const val ARG_DIMENSION = "dimension"
    }
}