package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity
import pl.mankevich.databaseapi.entity.LocationFilterEntity
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.LOCATION_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.LOCATION_PAGE_KEY_TABLE_NAME

@Dao
interface LocationPageKeyRoomDao {

    @Query("SELECT * FROM $LOCATION_PAGE_KEY_TABLE_NAME WHERE $LOCATION_ID_COLUMN =:locationId AND filter=:filter")
    suspend fun getPageKey(locationId: Int, filter: LocationFilterEntity): LocationPageKeyRoomEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPageKeysList(pageKeysList: List<LocationPageKeyRoomEntity>)

    @Query("DELETE FROM $LOCATION_PAGE_KEY_TABLE_NAME")
    suspend fun deleteAllPageKeys()

    @Query(
        "SELECT $LOCATION_ID_COLUMN FROM $LOCATION_PAGE_KEY_TABLE_NAME WHERE filter = :filter " +
                "ORDER BY $LOCATION_ID_COLUMN ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getLocationIdsByFilter(
        filter: LocationFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    @Query("SELECT COUNT(*) FROM $LOCATION_PAGE_KEY_TABLE_NAME WHERE filter=:filter")
    suspend fun getCount(filter: LocationFilterEntity): Int
}