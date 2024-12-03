package pl.mankevich.storageroom.dao

import pl.mankevich.storageroom.mapper.mapToLocationDto
import pl.mankevich.storageroom.mapper.mapToLocationEntity
import pl.mankevich.storageroom.room.dao.LocationRoomDao
import pl.mankevich.storageapi.dao.LocationDao
import pl.mankevich.storageapi.dto.LocationDto
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
        locationRoomDao.getLocationsList(limit = 20, offset = 0).map { it.mapToLocationDto() } //TODO add refactor and filter
}