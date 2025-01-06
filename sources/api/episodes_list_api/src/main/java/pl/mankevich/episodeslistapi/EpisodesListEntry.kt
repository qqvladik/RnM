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
            "&$ARG_SEASON={$ARG_SEASON}" +
            "&$ARG_EPISODE={$ARG_EPISODE}"

    final override val arguments = listOf(
        navArgument(ARG_NAME) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(ARG_SEASON) {
            type = NavType.IntType
        },
        navArgument(ARG_EPISODE) {
            type = NavType.IntType
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink {
            uriPattern = "$API_URL/$EPISODE_RELATIVE_PATH/" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_SEASON={$ARG_SEASON}" +
                    "&$ARG_EPISODE={$ARG_EPISODE}"
        })

    fun destination(
        name: String? = null,
        season: Int = -1,
        episode: Int = -1,
    ) = "$APP_ROUTE/$EPISODE_RELATIVE_PATH/" +
            "?$ARG_NAME=$name" +
            "&$ARG_SEASON=$season" +
            "&$ARG_EPISODE=$episode"

    companion object {
        const val ARG_NAME = "name"
        const val ARG_SEASON = "season"
        const val ARG_EPISODE = "episode"
    }
}