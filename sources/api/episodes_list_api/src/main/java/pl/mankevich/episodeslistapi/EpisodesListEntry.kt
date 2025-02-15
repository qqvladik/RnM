package pl.mankevich.episodeslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.core.API_URL
import pl.mankevich.core.EPISODE_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry
import pl.mankevich.episodeslistapi.EpisodesListEntry.Companion.ARG_NAME

@Serializable
data class EpisodesListRoute(
    @SerialName(ARG_NAME)
    val name: String? = null,
    val season: Int = -1,
    val episode: Int = -1, //TODO string
)

abstract class EpisodesListEntry : TypesafeComposableFeatureEntry<EpisodesListRoute>() {

    final override val featureRoute = EpisodesListRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(navDeepLink(
        route = featureRoute,
        basePath = "$API_URL/$EPISODE_RELATIVE_PATH",
    ) {
        uriPattern = "$API_URL/$EPISODE_RELATIVE_PATH/" +
                "?$ARG_NAME={$ARG_NAME}" +
                "&$ARG_SEASON={$ARG_SEASON}" +
                "&$ARG_EPISODE={$ARG_EPISODE}"
    })

    fun destination(
        name: String? = null,
        season: Int = -1, //TODO make nullable?
        episode: Int = -1, //TODO make nullable?
    ) = EpisodesListRoute(
        name = name,
        season = season,
        episode = episode
    )

    companion object {
        const val ARG_NAME = "name"
        const val ARG_SEASON = "season"
        const val ARG_EPISODE = "episode"
    }
}