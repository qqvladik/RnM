package pl.mankevich.databaseroom.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.EPISODE_TABLE_NAME

@Entity(tableName = EPISODE_TABLE_NAME)
data class EpisodeRoomEntity(
    @PrimaryKey @ColumnInfo(name = ID_COLUMN) var id: Int,
    @ColumnInfo(name = NAME_COLUMN) var name: String,
    @ColumnInfo(name = AIR_DATE_COLUMN) var airDate: String,
    @ColumnInfo(name = EPISODE_COLUMN) var episode: String,
) {
    companion object {
        const val EPISODE_TABLE_NAME = "episode"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val AIR_DATE_COLUMN = "air_date"
        const val EPISODE_COLUMN = "episode"
    }
}
