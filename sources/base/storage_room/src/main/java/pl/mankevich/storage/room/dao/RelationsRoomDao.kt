package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity.Companion.EPISODE_CHARACTER_TABLE_NAME
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity.Companion.EPISODE_ID_COLUMN
import pl.mankevich.storage.room.entity.LocationCharacterEntity

@Dao
interface RelationsRoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodeCharacterList(episodeCharacterList: List<EpisodeCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocationCharacterList(locationCharacterList: List<LocationCharacterEntity>)

    @Query(
        "SELECT $EPISODE_ID_COLUMN FROM $EPISODE_CHARACTER_TABLE_NAME " +
                "WHERE ${EpisodeCharacterEntity.CHARACTER_ID_COLUMN} = :characterId"
    )
    fun getEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>>
}