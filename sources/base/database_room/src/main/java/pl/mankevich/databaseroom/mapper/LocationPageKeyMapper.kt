package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity
import pl.mankevich.databaseapi.entity.LocationPageKeyEntity

fun LocationPageKeyRoomEntity.mapToEntity() = LocationPageKeyEntity(
    locationId = locationId,
    filter = filter,
    value = value,
    previousPageKey = previous,
    nextPageKey = next
)

fun LocationPageKeyEntity.mapToRoom() =
    LocationPageKeyRoomEntity(
        locationId = locationId,
        filter = filter,
        value = value,
        previous = previousPageKey,
        next = nextPageKey
    )