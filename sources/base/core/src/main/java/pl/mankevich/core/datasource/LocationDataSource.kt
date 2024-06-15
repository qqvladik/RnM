package pl.mankevich.core.datasource

import pl.mankevich.core.dto.Location

interface LocationDataSource {

    suspend fun insertListLocations(locations: List<Location>)

    suspend fun getLocationById(id: Int): Location?

    suspend fun getLocationsList(): List<Location>
}