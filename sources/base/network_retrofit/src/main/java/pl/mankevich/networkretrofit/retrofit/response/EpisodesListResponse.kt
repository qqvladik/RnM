package pl.mankevich.networkretrofit.retrofit.response

import com.google.gson.annotations.SerializedName

data class EpisodesListResponse(
    val info: InfoResponse,
    @SerializedName("results") val episodesResponse: List<EpisodeResponse>
)