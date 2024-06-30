package pl.mankevich.network.api.response

import com.google.gson.annotations.SerializedName

data class EpisodesListResponse(
    @SerializedName("results") val episodesResponse: List<EpisodeResponse>
)