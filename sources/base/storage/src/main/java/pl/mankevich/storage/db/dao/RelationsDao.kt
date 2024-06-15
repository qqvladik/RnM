package pl.mankevich.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity
import pl.mankevich.storage.db.entity.LocationCharacterEntity

@Dao
interface RelationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodeCharacterList(episodeCharacterList: List<EpisodeCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocationCharacterList(locationCharacterList: List<LocationCharacterEntity>)
}