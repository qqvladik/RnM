package pl.mankevich.data.mapper

import pl.mankevich.model.CharacterFilter
import pl.mankevich.remoteapi.query.CharacterFilterQuery
import pl.mankevich.databaseapi.entity.CharacterFilterEntity

fun CharacterFilter.mapToEntity() = CharacterFilterEntity(
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender
)

fun CharacterFilter.mapToQuery() = CharacterFilterQuery(
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender
)