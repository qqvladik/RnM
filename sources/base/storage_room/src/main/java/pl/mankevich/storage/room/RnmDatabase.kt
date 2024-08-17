package pl.mankevich.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.mankevich.storage.room.converter.FilterConverter
import pl.mankevich.storage.room.dao.CharacterRoomDao
import pl.mankevich.storage.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.storage.room.dao.EpisodeRoomDao
import pl.mankevich.storage.room.dao.LocationRoomDao
import pl.mankevich.storage.room.dao.RelationsRoomDao
import pl.mankevich.storage.room.entity.CharacterEntity
import pl.mankevich.storage.room.entity.CharacterPageKeyEntity
import pl.mankevich.storage.room.entity.EpisodeCharacterEntity
import pl.mankevich.storage.room.entity.EpisodeEntity
import pl.mankevich.storage.room.entity.LocationCharacterEntity
import pl.mankevich.storage.room.entity.LocationEntity

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

    abstract fun getCharacterDao(): CharacterRoomDao

    abstract fun getEpisodeDao(): EpisodeRoomDao

    abstract fun getLocationDao(): LocationRoomDao

    abstract fun getRelationsDao(): RelationsRoomDao

    abstract fun getCharacterPageKeyDao(): CharacterPageKeyRoomDao
}