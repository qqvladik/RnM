package pl.mankevich.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity.Companion.CHARACTER_ID_COLUMN
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity.Companion.EPISODE_CHARACTER_TABLE_NAME
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity.Companion.EPISODE_ID_COLUMN

@Entity(
    tableName = EPISODE_CHARACTER_TABLE_NAME,
    primaryKeys = [EPISODE_ID_COLUMN, CHARACTER_ID_COLUMN]
)
data class EpisodeCharacterEntity(
    @ColumnInfo(name = EPISODE_ID_COLUMN) var episodeId: Int,
    @ColumnInfo(name = CHARACTER_ID_COLUMN) var characterId: Int,
) {
    companion object {
        const val EPISODE_CHARACTER_TABLE_NAME = "episode_character"
        const val EPISODE_ID_COLUMN = "episode_id"
        const val CHARACTER_ID_COLUMN = "character_id"
    }
}
