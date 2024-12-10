package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.EPISODE_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.ID_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.NAME_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.EPISODE_TABLE_NAME

@Dao
interface EpisodeRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodesList(episodes: List<EpisodeRoomEntity>)

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    suspend fun getEpisodeById(id: Int): EpisodeRoomEntity

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeRoomEntity>>

    @Query(
        "SELECT * FROM $EPISODE_TABLE_NAME " +//todo add to lower case
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $EPISODE_COLUMN LIKE '%' || (:episode) || '%' " +
                "ORDER BY id ASC LIMIT (:limit) OFFSET (:offset)"
    )
    suspend fun getEpisodesList(
        name: String = "",
        episode: String = "",
        limit: Int,
        offset: Int
    ): List<EpisodeRoomEntity>
}