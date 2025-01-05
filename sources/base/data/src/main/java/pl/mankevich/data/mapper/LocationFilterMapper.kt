package pl.mankevich.data.mapper

import pl.mankevich.databaseapi.entity.LocationFilterEntity
import pl.mankevich.model.LocationFilter
import pl.mankevich.remoteapi.query.LocationFilterQuery

fun LocationFilter.mapToEntity() = LocationFilterEntity(
    name = name,
    type = type,
    dimension = dimension
)

fun LocationFilter.mapToQuery() = LocationFilterQuery(
    name = name,
    type = type,
    dimension = dimension
)