package pl.mankevich.network.api.response

import com.google.gson.annotations.SerializedName

data class CharactersListResponse(
    val info: InfoResponse,
    @SerializedName("results") val charactersResponse: List<CharacterResponse>
)