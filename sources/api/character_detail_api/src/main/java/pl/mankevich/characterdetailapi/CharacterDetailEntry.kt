package pl.mankevich.characterdetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pl.mankevich.core.API_URL
import pl.mankevich.core.APP_ROUTE
import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry

abstract class CharacterDetailEntry : AnimatedFeatureEntry() {

    final override val featureRoute = "$APP_ROUTE/$CHARACTER_RELATIVE_PATH/{$ARG_CHARACTER_ID}"

    final override val arguments = listOf(
        navArgument(ARG_CHARACTER_ID) {
            type = NavType.IntType
        }
    )

    final override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH/{$ARG_CHARACTER_ID}" })

    fun destination(characterId: Int) =
        "$APP_ROUTE/$CHARACTER_RELATIVE_PATH/$characterId"

    companion object {
        const val ARG_CHARACTER_ID = "characterId"
    }
}
