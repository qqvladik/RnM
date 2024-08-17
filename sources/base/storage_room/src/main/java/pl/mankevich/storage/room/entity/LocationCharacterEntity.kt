package pl.mankevich.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.storage.room.entity.LocationCharacterEntity.Companion.CHARACTER_ID_COLUMN
import pl.mankevich.storage.room.entity.LocationCharacterEntity.Companion.LOCATION_CHARACTER_TABLE_NAME
import pl.mankevich.storage.room.entity.LocationCharacterEntity.Companion.LOCATION_ID_COLUMN

@Entity(
    tableName = LOCATION_CHARACTER_TABLE_NAME,
    primaryKeys = [LOCATION_ID_COLUMN, CHARACTER_ID_COLUMN]
)
data class LocationCharacterEntity(
    @ColumnInfo(name = LOCATION_ID_COLUMN) var locationId: Int,
    @ColumnInfo(name = CHARACTER_ID_COLUMN) var characterId: Int,
) {
    companion object {
        const val LOCATION_CHARACTER_TABLE_NAME = "location_character"
        const val LOCATION_ID_COLUMN = "location_id"
        const val CHARACTER_ID_COLUMN = "character_id"
    }
}
