package pl.mankevich.data.mapper

import pl.mankevich.model.Location
import pl.mankevich.remoteapi.response.LocationResponse
import pl.mankevich.databaseapi.entity.LocationEntity

fun LocationResponse.mapToLocationDto() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun LocationEntity.mapToLocation() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)