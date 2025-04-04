package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity.Companion.CHARACTER_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity.Companion.EPISODE_CHARACTER_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity.Companion.EPISODE_ID_COLUMN

@Entity(
    tableName = EPISODE_CHARACTER_TABLE_NAME,
    primaryKeys = [EPISODE_ID_COLUMN, CHARACTER_ID_COLUMN]
)
data class EpisodeCharacterRoomEntity(
    @ColumnInfo(name = EPISODE_ID_COLUMN) var episodeId: Int,
    @ColumnInfo(name = CHARACTER_ID_COLUMN) var characterId: Int,
) {
    companion object {
        const val EPISODE_CHARACTER_TABLE_NAME = "episode_character"
        const val EPISODE_ID_COLUMN = "episode_id"
        const val CHARACTER_ID_COLUMN = "character_id"
    }
}
