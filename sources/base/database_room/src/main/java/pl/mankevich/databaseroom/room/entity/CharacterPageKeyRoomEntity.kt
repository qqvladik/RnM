package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.databaseapi.entity.CharacterFilterEntity
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.CHARACTER_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.CHARACTER_PAGE_KEY_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.FILTER_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.VALUE_COLUMN

@Entity(
    tableName = CHARACTER_PAGE_KEY_TABLE_NAME,
    primaryKeys = [CHARACTER_ID_COLUMN, FILTER_COLUMN, VALUE_COLUMN]
)
data class CharacterPageKeyRoomEntity(
    @ColumnInfo(name = CHARACTER_ID_COLUMN) val characterId: Int,
    @ColumnInfo(name = FILTER_COLUMN) val filter: CharacterFilterEntity,
    @ColumnInfo(name = VALUE_COLUMN) val value: Int,
    @ColumnInfo(name = PREVIOUS_COLUMN) val previous: Int? = null,
    @ColumnInfo(name = NEXT_COLUMN) val next: Int? = null
) {

    companion object {
        const val CHARACTER_PAGE_KEY_TABLE_NAME = "character_page_key"
        const val CHARACTER_ID_COLUMN = "character_id"
        const val FILTER_COLUMN = "filter"
        const val VALUE_COLUMN = "value"
        const val PREVIOUS_COLUMN = "previous"
        const val NEXT_COLUMN = "next"
    }
}