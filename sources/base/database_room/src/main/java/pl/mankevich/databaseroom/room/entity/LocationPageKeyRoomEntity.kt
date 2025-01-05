package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.databaseapi.entity.LocationFilterEntity
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.LOCATION_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.LOCATION_PAGE_KEY_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.FILTER_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity.Companion.VALUE_COLUMN

@Entity(
    tableName = LOCATION_PAGE_KEY_TABLE_NAME,
    primaryKeys = [LOCATION_ID_COLUMN, FILTER_COLUMN, VALUE_COLUMN]
)
data class LocationPageKeyRoomEntity(
    @ColumnInfo(name = LOCATION_ID_COLUMN) val locationId: Int,
    @ColumnInfo(name = FILTER_COLUMN) val filter: LocationFilterEntity,
    @ColumnInfo(name = VALUE_COLUMN) val value: Int,
    @ColumnInfo(name = PREVIOUS_COLUMN) val previous: Int? = null,
    @ColumnInfo(name = NEXT_COLUMN) val next: Int? = null
) {

    companion object {
        const val LOCATION_PAGE_KEY_TABLE_NAME = "location_page_key"
        const val LOCATION_ID_COLUMN = "location_id"
        const val FILTER_COLUMN = "filter"
        const val VALUE_COLUMN = "value"
        const val PREVIOUS_COLUMN = "previous"
        const val NEXT_COLUMN = "next"
    }
}