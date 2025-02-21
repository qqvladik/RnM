package pl.mankevich.characterslistapi

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_GENDER
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_NAME
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_SPECIES
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_STATUS
import pl.mankevich.characterslistapi.CharactersListEntry.Companion.ARG_TYPE
import pl.mankevich.core.API_URL
import pl.mankevich.core.CHARACTER_RELATIVE_PATH
import pl.mankevich.coreui.navigation.typesafe.TypesafeComposableFeatureEntry

@Serializable
data class CharactersListRoute(
    @SerialName(ARG_NAME)
    val name: String? = null,
    @SerialName(ARG_STATUS)
    val status: String? = null,
    @SerialName(ARG_SPECIES)
    val species: String? = null,
    @SerialName(ARG_GENDER)
    val gender: String? = null,
    @SerialName(ARG_TYPE)
    val type: String? = null
)

abstract class CharactersListEntry : TypesafeComposableFeatureEntry<CharactersListRoute>() {

    final override val featureRoute = CharactersListRoute::class

    final override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink(
            route = featureRoute,
            basePath = "$API_URL/$CHARACTER_RELATIVE_PATH"
        ) {
            uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH/" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_STATUS={$ARG_STATUS}" +
                    "&$ARG_SPECIES={$ARG_SPECIES}" +
                    "&$ARG_GENDER={$ARG_GENDER}" +
                    "&$ARG_TYPE={$ARG_TYPE}"
        },
        // Without slash (/) after relative path
        navDeepLink(
            route = featureRoute,
            basePath = "$API_URL/$CHARACTER_RELATIVE_PATH"
        ) {
            uriPattern = "$API_URL/$CHARACTER_RELATIVE_PATH" +
                    "?$ARG_NAME={$ARG_NAME}" +
                    "&$ARG_STATUS={$ARG_STATUS}" +
                    "&$ARG_SPECIES={$ARG_SPECIES}" +
                    "&$ARG_GENDER={$ARG_GENDER}" +
                    "&$ARG_TYPE={$ARG_TYPE}"
        },
    )

    fun destination(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null,
        type: String? = null
    ) = CharactersListRoute(
        name = name,
        status = status,
        species = species,
        gender = gender,
        type = type
    )

    companion object {
        const val ARG_NAME = "name"
        const val ARG_STATUS = "status"
        const val ARG_SPECIES = "species"
        const val ARG_GENDER = "gender"
        const val ARG_TYPE = "type"
    }
}
