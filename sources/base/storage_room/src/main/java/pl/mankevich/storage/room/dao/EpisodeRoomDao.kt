package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mankevich.storage.room.entity.EpisodeEntity
import pl.mankevich.storage.room.entity.EpisodeEntity.Companion.EPISODE_COLUMN
import pl.mankevich.storage.room.entity.EpisodeEntity.Companion.ID_COLUMN
import pl.mankevich.storage.room.entity.EpisodeEntity.Companion.NAME_COLUMN
import pl.mankevich.storage.room.entity.EpisodeEntity.Companion.EPISODE_TABLE_NAME

@Dao
interface EpisodeRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodesList(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    suspend fun getEpisodeById(id: Int): EpisodeEntity

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    fun getEpisodesByIds(ids: List<Int>): Flow<List<EpisodeEntity>>

    @Query(
        "SELECT * FROM $EPISODE_TABLE_NAME " +//todo add to lower case
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $EPISODE_COLUMN LIKE '%' || (:episode) || '%' " +
                "ORDER BY id ASC LIMIT (:limit) OFFSET (:offset)"
    )
    suspend fun getEpisodesList(
        name: String = "",
        episode: String = "",
        limit: Int = 20,
        offset: Int = 0 //TODO check
    ): List<EpisodeEntity>
}