package pl.mankevich.databaseapi.dao

import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseapi.entity.LocationEntity
import pl.mankevich.databaseapi.entity.LocationFilterEntity
import pl.mankevich.databaseapi.entity.LocationPageKeyEntity

abstract class LocationDao : DaoBase() {

    abstract suspend fun insertLocation(location: LocationEntity)

    abstract suspend fun insertLocationsList(locations: List<LocationEntity>)

    abstract fun getLocationById(id: Int): Flow<LocationEntity>

    abstract suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntity>

    abstract suspend fun getLocationsList(
        locationFilterEntity: LocationFilterEntity,
        limit: Int,
        offset: Int
    ): List<LocationEntity>

    abstract suspend fun getLocationsCount(locationFilterEntity: LocationFilterEntity): Int

    abstract suspend fun getLocationIds(
        locationFilterEntity: LocationFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    abstract suspend fun insertPageKeysList(pageKeys: List<LocationPageKeyEntity>)

    abstract suspend fun getPageKey(locationId: Int, locationFilterEntity: LocationFilterEntity): LocationPageKeyEntity?

    abstract suspend fun getPageKeysCount(locationFilterEntity: LocationFilterEntity): Int
}