package pl.mankevich.databaseapi.dao

import pl.mankevich.databaseapi.entity.LocationEntity

interface LocationDao {

    suspend fun insertListLocations(locations: List<LocationEntity>)

    suspend fun getLocationById(id: Int): LocationEntity?

    suspend fun getLocationsList(): List<LocationEntity>
}