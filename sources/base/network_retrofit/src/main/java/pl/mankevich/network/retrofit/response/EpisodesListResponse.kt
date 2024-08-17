package pl.mankevich.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class EpisodesListResponse(
    val info: InfoResponse,
    @SerializedName("results") val episodesResponse: List<EpisodeResponse>
)