package pl.mankevich.characterslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class CharactersListEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$CHARACTER_RELATIVE_PATH/" +
            "?$ARG_NAME={$ARG_NAME}" +
            "&$ARG_STATUS={$ARG_STATUS}" +
            "&$ARG_SPECIES={$ARG_SPECIES}" +
            "&$ARG_GENDER={$ARG_GENDER}" +
            "&$ARG_TYPE={$ARG_TYPE}"

    final override val arguments = listOf(
        navArgument(ARG_NAME) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_STATUS) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_SPECIES) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_GENDER) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_TYPE) {
            type = NavType.StringType
            nullable = true
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink {
            uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH/" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_STATUS={$ARG_STATUS}" +
                    "&$ARG_SPECIES={$ARG_SPECIES}" +
                    "&$ARG_GENDER={$ARG_GENDER}" +
                    "&$ARG_TYPE={$ARG_TYPE}"
        })

    fun destination(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null,
        type: String? = null
    ) = "$APP_ROUTE/$CHARACTER_RELATIVE_PATH/" +
            "?$ARG_NAME=$name" +
            "&$ARG_STATUS=$status" +
            "&$ARG_SPECIES=$species" +
            "&$ARG_GENDER=$gender" +
            "&$ARG_TYPE=$type"

    companion object {
        const val ARG_NAME = "name"
        const val ARG_STATUS = "status"
        const val ARG_SPECIES = "species"
        const val ARG_GENDER = "gender"
        const val ARG_TYPE = "type"
    }
}