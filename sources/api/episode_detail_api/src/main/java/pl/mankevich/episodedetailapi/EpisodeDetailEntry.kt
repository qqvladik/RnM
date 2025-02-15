package pl.mankevich.episodedetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.EPISODE_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry
import pl.mankevich.episodedetailapi.EpisodeDetailEntry.Companion.ARG_EPISODE_ID

@Serializable
data class EpisodeDetailRoute(
    @SerialName(ARG_EPISODE_ID)
    val episodeId: Int
)

abstract class EpisodeDetailEntry : TypesafeComposableFeatureEntry<EpisodeDetailRoute>() {

    final override val featureRoute = EpisodeDetailRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(navDeepLink(
        route = featureRoute,
        basePath = "$APP_ROUTE/$EPISODE_RELATIVE_PATH",
    ) {
        uriPattern = "$API_URL/$EPISODE_RELATIVE_PATH/{$ARG_EPISODE_ID}"
    })

    fun destination(episodeId: Int) =
        EpisodeDetailRoute(episodeId = episodeId)

    companion object {
        const val ARG_EPISODE_ID = "episodeId"
    }
}
