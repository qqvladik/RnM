package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity
import pl.mankevich.databaseapi.entity.EpisodePageKeyEntity

fun EpisodePageKeyRoomEntity.mapToEntity() = EpisodePageKeyEntity(
    episodeId = episodeId,
    filter = filter,
    value = value,
    previousPageKey = previous,
    nextPageKey = next
)

fun EpisodePageKeyEntity.mapToRoom() =
    EpisodePageKeyRoomEntity(
        episodeId = episodeId,
        filter = filter,
        value = value,
        previous = previousPageKey,
        next = nextPageKey
    )