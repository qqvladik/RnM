package pl.mankevich.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.CHARACTER_TABLE_NAME

@Entity(tableName = CHARACTER_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = ID_COLUMN) var id: Int,
    @ColumnInfo(name = NAME_COLUMN) var name: String,
    @ColumnInfo(name = STATUS_COLUMN) var status: String,
    @ColumnInfo(name = SPECIES_COLUMN) var species: String,
    @ColumnInfo(name = TYPE_COLUMN) var type: String,
    @ColumnInfo(name = GENDER_COLUMN) var gender: String,
    @Embedded(prefix = ORIGIN_COLUMN.plus("_")) var origin: LocationEmbedded,
    @Embedded(prefix = LOCATION_COLUMN.plus("_")) var location: LocationEmbedded,
    @ColumnInfo(name = IMAGE_COLUMN) var image: String,
) {
    companion object {
        const val CHARACTER_TABLE_NAME = "character"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val STATUS_COLUMN = "status"
        const val SPECIES_COLUMN = "species"
        const val TYPE_COLUMN = "type"
        const val GENDER_COLUMN = "gender"
        const val ORIGIN_COLUMN = "origin"
        const val LOCATION_COLUMN = "location"
        const val IMAGE_COLUMN = "image"
    }
}

data class LocationEmbedded(
    @ColumnInfo(name = ID_COLUMN) var id: Int,
    @ColumnInfo(name = NAME_COLUMN) var name: String
) {
    companion object {
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
    }
}
