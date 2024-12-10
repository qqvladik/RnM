package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseapi.entity.EpisodeEntity
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity

fun EpisodeEntity.mapToRoom() = EpisodeRoomEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)

fun EpisodeRoomEntity.mapToEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)