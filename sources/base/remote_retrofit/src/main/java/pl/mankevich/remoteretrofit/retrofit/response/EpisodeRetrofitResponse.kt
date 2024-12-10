package pl.mankevich.remoteretrofit.retrofit.response

import com.google.gson.annotations.SerializedName

data class EpisodeRetrofitResponse(
    val id: Int,
    val name: String,
    @SerializedName("air_date") val airDate: String,
    val episode: String,
    val characters: List<String>
)