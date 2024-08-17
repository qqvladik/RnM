package pl.mankevich.storageapi.dao

import pl.mankevich.storageapi.dto.LocationDto

interface LocationDao {

    suspend fun insertListLocations(locations: List<LocationDto>)

    suspend fun getLocationById(id: Int): LocationDto?

    suspend fun getLocationsList(): List<LocationDto>
}