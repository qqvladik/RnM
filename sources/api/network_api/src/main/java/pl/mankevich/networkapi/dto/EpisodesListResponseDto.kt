package pl.mankevich.networkapi.dto

data class EpisodesListResponseDto(
    val info: InfoResponseDto,
    val episodesResponse: List<EpisodeResponseDto>
)