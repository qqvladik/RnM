package pl.mankevich.data.mapper

import pl.mankevich.model.Episode
import pl.mankevich.networkapi.dto.EpisodeResponseDto
import pl.mankevich.storageapi.dto.EpisodeDto

fun EpisodeDto.mapToEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

fun EpisodeResponseDto.mapToEpisodeDto() = EpisodeDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)