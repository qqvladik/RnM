package pl.mankevich.storageapi.datasource

import pl.mankevich.storageapi.dto.LocationDto

interface LocationStorageDataSource {

    suspend fun insertListLocations(locations: List<LocationDto>)

    suspend fun getLocationById(id: Int): LocationDto?

    suspend fun getLocationsList(): List<LocationDto>
}