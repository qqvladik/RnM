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
import pl.mankevich.databaseroom.room.entity.LocationCharacterRoomEntity.Companion.LOCATION_CHARACTER_TABLE_NAME

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

    @Query(
        "SELECT ${EpisodeCharacterRoomEntity.CHARACTER_ID_COLUMN} FROM $EPISODE_CHARACTER_TABLE_NAME " +
                "WHERE $EPISODE_ID_COLUMN = :episodeId"
    )
    fun getCharacterIdsByEpisodeId(episodeId: Int): Flow<List<Int>>

    @Query(
        "SELECT ${LocationCharacterRoomEntity.CHARACTER_ID_COLUMN} FROM $LOCATION_CHARACTER_TABLE_NAME " +
                "WHERE ${LocationCharacterRoomEntity.LOCATION_ID_COLUMN} = :locationId"
    )
    fun getCharacterIdsByLocationId(locationId: Int): Flow<List<Int>>

}