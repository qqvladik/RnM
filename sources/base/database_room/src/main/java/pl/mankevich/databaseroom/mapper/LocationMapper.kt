package pl.mankevich.databaseroom.mapper

import pl.mankevich.databaseapi.entity.LocationEntity
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity

fun LocationEntity.mapToRoom() = LocationRoomEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationRoomEntity.mapToEntity() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)