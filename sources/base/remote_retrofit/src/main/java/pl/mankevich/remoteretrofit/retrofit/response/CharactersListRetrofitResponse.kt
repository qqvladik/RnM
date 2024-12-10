package pl.mankevich.remoteretrofit.retrofit.response

import com.google.gson.annotations.SerializedName

data class CharactersListRetrofitResponse(
    val info: InfoRetrofitResponse,
    @SerializedName("results") val charactersResponse: List<CharacterRetrofitResponse>
)