package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.mankevich.databaseapi.entity.EpisodeFilterEntity
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.EPISODE_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.EPISODE_PAGE_KEY_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.FILTER_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.VALUE_COLUMN

@Entity(
    tableName = EPISODE_PAGE_KEY_TABLE_NAME,
    primaryKeys = [EPISODE_ID_COLUMN, FILTER_COLUMN, VALUE_COLUMN]
)
data class EpisodePageKeyRoomEntity(
    @ColumnInfo(name = EPISODE_ID_COLUMN) val episodeId: Int,
    @ColumnInfo(name = FILTER_COLUMN) val filter: EpisodeFilterEntity,
    @ColumnInfo(name = VALUE_COLUMN) val value: Int,
    @ColumnInfo(name = PREVIOUS_COLUMN) val previous: Int? = null,
    @ColumnInfo(name = NEXT_COLUMN) val next: Int? = null
) {

    companion object {
        const val EPISODE_PAGE_KEY_TABLE_NAME = "episode_page_key"
        const val EPISODE_ID_COLUMN = "episode_id"
        const val FILTER_COLUMN = "filter"
        const val VALUE_COLUMN = "value"
        const val PREVIOUS_COLUMN = "previous"
        const val NEXT_COLUMN = "next"
    }
}