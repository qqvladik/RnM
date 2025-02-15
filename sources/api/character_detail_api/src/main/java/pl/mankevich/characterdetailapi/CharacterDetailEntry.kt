package pl.mankevich.characterdetailapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.characterdetailapi.CharacterDetailEntry.Companion.ARG_CHARACTER_ID
import pl.mankevich.core.API_URL
import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry

@Serializable
data class CharacterDetailRoute(
    @SerialName(ARG_CHARACTER_ID)
    val characterId: Int
)

abstract class CharacterDetailEntry : TypesafeComposableFeatureEntry<CharacterDetailRoute>() {

    final override val featureRoute = CharacterDetailRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink(
            route = featureRoute,
            basePath = "$API_URL/$CHARACTER_RELATIVE_PATH"
        ) {
            uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH/{$ARG_CHARACTER_ID}"
        }
    )

    fun destination(characterId: Int) =
        CharacterDetailRoute(characterId = characterId)

    companion object {
        const val ARG_CHARACTER_ID = "characterId"
    }
}
