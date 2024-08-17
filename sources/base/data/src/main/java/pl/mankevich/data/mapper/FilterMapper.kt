package pl.mankevich.data.mapper

import pl.mankevich.model.Filter
import pl.mankevich.networkapi.dto.FilterQueryDto
import pl.mankevich.storageapi.dto.FilterDto

fun Filter.mapToFilterDto() = FilterDto(
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender
)

fun Filter.mapToFilterQuery() = FilterQueryDto(
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender
)