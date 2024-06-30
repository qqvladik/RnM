package pl.mankevich.storage.datasource

import pl.mankevich.storageapi.dto.LocationDto
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.entity.LocationEntity
import pl.mankevich.storageapi.datasource.LocationStorageDataSource
import javax.inject.Inject

class LocationStorageDataSourceImpl
@Inject constructor(
    private val locationDao: LocationDao
) : LocationStorageDataSource {
    override suspend fun insertListLocations(locations: List<LocationDto>) =
        locationDao.insertLocationsList(locations.map { it.mapToLocationEntity() })

    override suspend fun getLocationById(id: Int): LocationDto =
        locationDao.getLocationById(id).mapToLocationDto()

    override suspend fun getLocationsList(): List<LocationDto> =
        locationDao.getLocationsList().map { it.mapToLocationDto() }
}

private fun LocationDto.mapToLocationEntity() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

private fun LocationEntity.mapToLocationDto() = LocationDto(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)