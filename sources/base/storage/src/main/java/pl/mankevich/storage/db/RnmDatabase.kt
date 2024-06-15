package pl.mankevich.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.dao.RelationsDao
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.EpisodeCharacterEntity
import pl.mankevich.storage.db.entity.EpisodeEntity
import pl.mankevich.storage.db.entity.LocationCharacterEntity
import pl.mankevich.storage.db.entity.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class,
        EpisodeCharacterEntity::class,
        LocationCharacterEntity::class
    ],
    version = RnmDatabase.DATABASE_VERSION
)
abstract class RnmDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "rnm_database"
    }

    abstract fun getCharacterDao(): CharacterDao

    abstract fun getEpisodeDao(): EpisodeDao

    abstract fun getLocationDao(): LocationDao

    abstract fun getRelationsDao(): RelationsDao
}