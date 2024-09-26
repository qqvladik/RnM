package pl.mankevich.data.mapper

import pl.mankevich.model.Location
import pl.mankevich.networkapi.dto.LocationResponseDto
import pl.mankevich.storageapi.dto.LocationDto

fun LocationResponseDto.mapToLocationDto() = LocationDto(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationDto.mapToLocation() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)