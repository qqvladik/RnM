package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.EPISODE_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.EPISODE_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.ID_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity.Companion.NAME_COLUMN

@Dao
interface EpisodeRoomDao {

    @Upsert
    suspend fun insertEpisode(episode: EpisodeRoomEntity)

    @Upsert
    suspend fun insertEpisodesList(episodes: List<EpisodeRoomEntity>)

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    fun getEpisodeById(id: Int): Flow<EpisodeRoomEntity?>

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeRoomEntity>

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    fun getEpisodesFlowByIds(ids: List<Int>): Flow<List<EpisodeRoomEntity>>

    @Query(
        "SELECT * FROM $EPISODE_TABLE_NAME " +
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

    @Query(
        "SELECT COUNT(*) FROM ( $EPISODE_TABLE_NAME )" +
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $EPISODE_COLUMN LIKE '%' || (:episode) || '%' "
    )
    suspend fun getCount(
        name: String = "",
        episode: String = "",
    ): Int
}