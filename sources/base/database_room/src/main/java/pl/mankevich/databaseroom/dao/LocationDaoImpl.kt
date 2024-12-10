package pl.mankevich.databaseroom.dao

import pl.mankevich.databaseroom.mapper.mapToEntity
import pl.mankevich.databaseroom.mapper.mapToRoom
import pl.mankevich.databaseroom.room.dao.LocationRoomDao
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.entity.LocationEntity
import javax.inject.Inject

class LocationDaoImpl
@Inject constructor(
    private val locationRoomDao: LocationRoomDao
) : LocationDao {

    override suspend fun insertListLocations(locations: List<LocationEntity>) =
        locationRoomDao.insertLocationsList(locations.map { it.mapToRoom() })

    override suspend fun getLocationById(id: Int): LocationEntity =
        locationRoomDao.getLocationById(id).mapToEntity()

    override suspend fun getLocationsList(): List<LocationEntity> =
        locationRoomDao.getLocationsList(limit = 20, offset = 0).map { it.mapToEntity() } //TODO add refactor and filter
}
