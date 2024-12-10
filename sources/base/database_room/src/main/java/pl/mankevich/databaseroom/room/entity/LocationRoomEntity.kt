package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity.Companion.LOCATION_TABLE_NAME

@Entity(tableName = LOCATION_TABLE_NAME)
data class LocationRoomEntity(
    @PrimaryKey @ColumnInfo(name = ID_COLUMN) var id: Int,
    @ColumnInfo(name = NAME_COLUMN) var name: String,
    @ColumnInfo(name = TYPE_COLUMN) var type: String,
    @ColumnInfo(name = DIMENSION_COLUMN) var dimension: String,
) {
    companion object {
        const val LOCATION_TABLE_NAME = "location"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val TYPE_COLUMN = "type"
        const val DIMENSION_COLUMN = "dimension"
    }
}
