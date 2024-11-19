package pl.mankevich.storageroom.mapper

import pl.mankevich.storageroom.room.entity.EpisodeEntity
import pl.mankevich.storageapi.dto.EpisodeDto

fun EpisodeDto.mapToEpisodeEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

fun EpisodeEntity.mapToEpisodeDto() = EpisodeDto(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)