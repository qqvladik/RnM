package pl.mankevich.characterslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.core.navigation.ComposableFeatureEntry

abstract class CharactersListEntry: ComposableFeatureEntry {

    final override val featureRoute = "$APP_ROUTE/$CHARACTER_RELATIVE_PATH"

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH" })

    fun destination() = featureRoute
}