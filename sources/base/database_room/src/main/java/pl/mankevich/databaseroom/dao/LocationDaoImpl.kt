package pl.mankevich.databaseroom.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.entity.LocationEntity
import pl.mankevich.databaseapi.entity.LocationFilterEntity
import pl.mankevich.databaseapi.entity.LocationPageKeyEntity
import pl.mankevich.databaseroom.mapper.mapToEntity
import pl.mankevich.databaseroom.mapper.mapToRoom
import pl.mankevich.databaseroom.room.dao.LocationPageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.LocationRoomDao
import javax.inject.Inject

class LocationDaoImpl
@Inject constructor(
    private val locationRoomDao: LocationRoomDao,
    private val locationPageKeyRoomDao: LocationPageKeyRoomDao
) : LocationDao() {

    override suspend fun insertLocation(location: LocationEntity) {
        locationRoomDao.insertLocation(location.mapToRoom())
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun insertLocationsList(locations: List<LocationEntity>) {
        locationRoomDao.insertLocationsList(locations.map { it.mapToRoom() })
        tableUpdateNotifier.notifyListeners()
    }

    override fun getLocationById(id: Int): Flow<LocationEntity?> =
        locationRoomDao.getLocationById(id).map { it?.mapToEntity() }

    override suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntity> =
        locationRoomDao.getLocationsByIds(ids).map { it.mapToEntity() }

    override suspend fun getLocationsList(
        locationFilterEntity: LocationFilterEntity,
        limit: Int,
        offset: Int
    ): List<LocationEntity> =
        locationRoomDao.getLocationsList(
            name = locationFilterEntity.name,
            type = locationFilterEntity.type,
            dimension = locationFilterEntity.dimension,
            limit = limit,
            offset = offset
        ).map { it.mapToEntity() }

    override suspend fun getLocationsCount(locationFilterEntity: LocationFilterEntity): Int =
        locationRoomDao.getCount(
            name = locationFilterEntity.name,
            type = locationFilterEntity.type,
            dimension = locationFilterEntity.dimension
        )

    override suspend fun getLocationIds(
        locationFilterEntity: LocationFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int> =
        locationPageKeyRoomDao.getLocationIdsByFilter(locationFilterEntity, limit, offset)

    override suspend fun insertPageKeysList(pageKeys: List<LocationPageKeyEntity>) {
        locationPageKeyRoomDao.insertPageKeysList(
            pageKeys.map { it.mapToRoom() }
        )
        tableUpdateNotifier.notifyListeners()
    }

    override suspend fun getPageKey(
        locationId: Int,
        locationFilterEntity: LocationFilterEntity
    ): LocationPageKeyEntity? =
        locationPageKeyRoomDao.getPageKey(locationId, locationFilterEntity)?.mapToEntity()

    override suspend fun getPageKeysCount(locationFilterEntity: LocationFilterEntity): Int =
        locationPageKeyRoomDao.getCount(locationFilterEntity)
}
