package pl.mankevich.domainapi.result

import pl.mankevich.model.Location

data class LocationDetailResult(
    val isOnline: Boolean?,
    val location: Location?
)