package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.DIMENSION_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.ID_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.LOCATION_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.NAME_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.TYPE_COLUMN

@Dao
interface LocationRoomDao {

    @Upsert
    suspend fun insertLocation(location: LocationRoomEntity)

    @Upsert
    suspend fun insertLocationsList(locations: List<LocationRoomEntity>)

    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    fun getLocationById(id: Int): Flow<LocationRoomEntity>

    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    suspend fun getLocationsByIds(ids: List<Int>): List<LocationRoomEntity>

    @Query(
        "SELECT * FROM $LOCATION_TABLE_NAME " +
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $TYPE_COLUMN LIKE '%' || (:type) || '%' " +
                "AND $DIMENSION_COLUMN LIKE '%' || (:dimension) || '%' " +
                "ORDER BY id ASC LIMIT (:limit) OFFSET (:offset)"
    )
    suspend fun getLocationsList(
        name: String = "",
        type: String = "",
        dimension: String = "",
        limit: Int,
        offset: Int
    ): List<LocationRoomEntity>

    @Query(
        "SELECT COUNT(*) FROM ( $LOCATION_TABLE_NAME )" +
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $TYPE_COLUMN LIKE '%' || (:type) || '%' " +
                "AND $DIMENSION_COLUMN LIKE '%' || (:dimension) || '%' "
    )
    suspend fun getCount(
        name: String = "",
        type: String = "",
        dimension: String = "",
    ): Int
}