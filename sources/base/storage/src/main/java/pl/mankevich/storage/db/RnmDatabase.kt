package pl.mankevich.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.mankevich.storage.db.converter.FilterConverter
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.dao.CharacterPageKeyDao
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.dao.RelationsDao
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.CharacterPageKeyEntity
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
        LocationCharacterEntity::class,
        CharacterPageKeyEntity::class,
    ],
    version = RnmDatabase.DATABASE_VERSION
)
@TypeConverters(FilterConverter::class)
abstract class RnmDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "rnm_database"
    }

    abstract fun getCharacterDao(): CharacterDao

    abstract fun getEpisodeDao(): EpisodeDao

    abstract fun getLocationDao(): LocationDao

    abstract fun getRelationsDao(): RelationsDao

    abstract fun getCharacterPageKeyDao(): CharacterPageKeyDao
}