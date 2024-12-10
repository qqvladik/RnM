package pl.mankevich.remoteapi.response

data class EpisodesListResponse(
    val info: InfoResponse,
    val episodesResponse: List<EpisodeResponse>
)