package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.storage.room.entity.LocationEntity
import pl.mankevich.storage.room.entity.LocationEntity.Companion.DIMENSION_COLUMN
import pl.mankevich.storage.room.entity.LocationEntity.Companion.ID_COLUMN
import pl.mankevich.storage.room.entity.LocationEntity.Companion.NAME_COLUMN
import pl.mankevich.storage.room.entity.LocationEntity.Companion.TYPE_COLUMN
import pl.mankevich.storage.room.entity.LocationEntity.Companion.LOCATION_TABLE_NAME

@Dao
interface LocationRoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocationsList(locations: List<LocationEntity>)

    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    suspend fun getLocationById(id: Int): LocationEntity

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE ID_COLUMN in (:ids)")
//    suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntity>

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME")
//    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM $LOCATION_TABLE_NAME " +//todo add to lowercase
            "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
            "AND $TYPE_COLUMN LIKE '%' || (:type) || '%' " +
            "AND $DIMENSION_COLUMN LIKE '%' || (:dimension) || '%' " +
            "ORDER BY id ASC LIMIT (:limit) OFFSET (:offset)")
    suspend fun getLocationsList(
        name: String = "",
        type: String = "",
        dimension: String = "",
        limit: Int = 20,
        offset: Int = 0 //TODO check
    ): List<LocationEntity>
}