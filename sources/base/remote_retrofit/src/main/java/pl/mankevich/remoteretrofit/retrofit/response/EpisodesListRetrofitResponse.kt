package pl.mankevich.remoteretrofit.retrofit.response

import com.google.gson.annotations.SerializedName

data class EpisodesListRetrofitResponse(
    val info: InfoRetrofitResponse,
    @SerializedName("results") val episodesResponse: List<EpisodeRetrofitResponse>
)