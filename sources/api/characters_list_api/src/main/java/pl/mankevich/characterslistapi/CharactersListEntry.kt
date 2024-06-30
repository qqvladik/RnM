package pl.mankevich.characterslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.navigation.ComposableFeatureEntry

const val CHARACTERS_LIST_PATH = "character"

abstract class CharactersListEntry: ComposableFeatureEntry {

    final override val featureRoute = "$APP_ROUTE/$CHARACTERS_LIST_PATH"

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$API_URL/$CHARACTERS_LIST_PATH" })

    fun destination() = featureRoute
}