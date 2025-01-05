package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity
import pl.mankevich.databaseapi.entity.EpisodeFilterEntity
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.EPISODE_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity.Companion.EPISODE_PAGE_KEY_TABLE_NAME

@Dao
interface EpisodePageKeyRoomDao {

    @Query("SELECT * FROM $EPISODE_PAGE_KEY_TABLE_NAME WHERE $EPISODE_ID_COLUMN =:episodeId AND filter=:filter")
    suspend fun getPageKey(episodeId: Int, filter: EpisodeFilterEntity): EpisodePageKeyRoomEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPageKeysList(pageKeysList: List<EpisodePageKeyRoomEntity>)

    @Query("DELETE FROM $EPISODE_PAGE_KEY_TABLE_NAME")
    suspend fun deleteAllPageKeys()

    @Query(
        "SELECT $EPISODE_ID_COLUMN FROM $EPISODE_PAGE_KEY_TABLE_NAME WHERE filter = :filter " +
                "ORDER BY $EPISODE_ID_COLUMN ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getEpisodeIdsByFilter(
        filter: EpisodeFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    @Query("SELECT COUNT(*) FROM $EPISODE_PAGE_KEY_TABLE_NAME WHERE filter=:filter")
    suspend fun getCount(filter: EpisodeFilterEntity): Int
}