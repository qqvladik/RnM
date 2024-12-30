package pl.mankevich.episodedetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.EPISODE_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class EpisodeDetailEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$EPISODE_RELATIVE_PATH/{$ARG_EPISODE_ID}"

    final override val arguments = listOf(
        navArgument(ARG_EPISODE_ID) {
            type = NavType.IntType
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$API_URL/$EPISODE_RELATIVE_PATH/{$ARG_EPISODE_ID}" })

    fun destination(episodeId: Int) =
        "$APP_ROUTE/$EPISODE_RELATIVE_PATH/$episodeId"

    companion object {
        const val ARG_EPISODE_ID = "episodeId"
    }
}
