package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity.Companion.EPISODE_CHARACTER_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity.Companion.EPISODE_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationCharacterRoomEntity

@Dao
interface RelationsRoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodeCharacterList(episodeCharacterList: List<EpisodeCharacterRoomEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocationCharacterList(locationCharacterList: List<LocationCharacterRoomEntity>)

    @Query(
        "SELECT $EPISODE_ID_COLUMN FROM $EPISODE_CHARACTER_TABLE_NAME " +
                "WHERE ${EpisodeCharacterRoomEntity.CHARACTER_ID_COLUMN} = :characterId"
    )
    fun getEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>>
}