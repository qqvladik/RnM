package pl.mankevich.network.api.response

import com.google.gson.annotations.SerializedName

data class CharactersListResponse(
    @SerializedName("results") val charactersResponse: List<CharacterResponse>
)