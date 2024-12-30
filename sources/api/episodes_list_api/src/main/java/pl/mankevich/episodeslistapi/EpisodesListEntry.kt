package pl.mankevich.episodeslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.EPISODE_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class EpisodesListEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$EPISODE_RELATIVE_PATH/" +
            "?$ARG_NAME={$ARG_NAME}" +
            "&$ARG_EPISODE={$ARG_EPISODE}"

    final override val arguments = listOf(
        navArgument(ARG_NAME) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_EPISODE) {
            type = NavType.StringType
            nullable = true
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink {
            uriPattern = "$API_URL/$EPISODE_RELATIVE_PATH/" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_EPISODE={$ARG_EPISODE}"
        })

    fun destination(
        name: String? = null,
        episode: String? = null,
    ) = "$APP_ROUTE/$EPISODE_RELATIVE_PATH/" +
            "?$ARG_NAME=$name" +
            "&$ARG_EPISODE=$episode"

    companion object {
        const val ARG_NAME = "name"
        const val ARG_EPISODE = "episode"
    }
}