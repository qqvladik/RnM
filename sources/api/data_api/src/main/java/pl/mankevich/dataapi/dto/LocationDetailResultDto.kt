package pl.mankevich.dataapi.dto

import pl.mankevich.model.Location

data class LocationDetailResultDto(
    val isOnline: Boolean?,
    val location: Location?
)