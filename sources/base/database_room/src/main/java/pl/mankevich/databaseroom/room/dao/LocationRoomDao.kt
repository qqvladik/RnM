package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.DIMENSION_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.ID_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.NAME_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.TYPE_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.LOCATION_TABLE_NAME

@Dao
interface LocationRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationsList(locations: List<LocationRoomEntity>)

    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    suspend fun getLocationById(id: Int): LocationRoomEntity

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE ID_COLUMN in (:ids)")
//    suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntity>

    @Query(
        "SELECT * FROM $LOCATION_TABLE_NAME " +//todo add to lowercase
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
}