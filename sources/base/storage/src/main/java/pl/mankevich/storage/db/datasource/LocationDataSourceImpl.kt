package pl.mankevich.storage.db.datasource

import pl.mankevich.core.datasource.LocationDataSource
import pl.mankevich.core.dto.Location
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.entity.LocationEntity
import javax.inject.Inject

class LocationDataSourceImpl
@Inject constructor(
    private val locationDao: LocationDao
) : LocationDataSource {
    override suspend fun insertListLocations(locations: List<Location>) =
        locationDao.insertLocationsList(locations.map { it.mapToLocationEntity() })

    override suspend fun getLocationById(id: Int): Location? =
        locationDao.getLocationById(id)?.mapToLocation()

    override suspend fun getLocationsList(): List<Location> =
        locationDao.getLocationsList().map { it.mapToLocation() }
}

private fun Location.mapToLocationEntity() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

private fun LocationEntity.mapToLocation() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)