package pl.mankevich.storage.mapper

import pl.mankevich.storage.room.entity.LocationEntity
import pl.mankevich.storageapi.dto.LocationDto

fun LocationDto.mapToLocationEntity() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationEntity.mapToLocationDto() = LocationDto(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)