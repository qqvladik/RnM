package pl.mankevich.storage.dao

import pl.mankevich.storageapi.dto.LocationDto
import pl.mankevich.storage.room.dao.LocationRoomDao
import pl.mankevich.storage.room.entity.LocationEntity
import pl.mankevich.storageapi.dao.LocationDao
import javax.inject.Inject

class LocationDaoImpl
@Inject constructor(
    private val locationRoomDao: LocationRoomDao
) : LocationDao {
    override suspend fun insertListLocations(locations: List<LocationDto>) =
        locationRoomDao.insertLocationsList(locations.map { it.mapToLocationEntity() })

    override suspend fun getLocationById(id: Int): LocationDto =
        locationRoomDao.getLocationById(id).mapToLocationDto()

    override suspend fun getLocationsList(): List<LocationDto> =
        locationRoomDao.getLocationsList().map { it.mapToLocationDto() }
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