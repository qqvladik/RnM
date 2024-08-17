package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity
import pl.mankevich.storage.room.entity.LocationCharacterEntity

@Dao
interface RelationsRoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodeCharacterList(episodeCharacterList: List<EpisodeCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocationCharacterList(locationCharacterList: List<LocationCharacterEntity>)
}