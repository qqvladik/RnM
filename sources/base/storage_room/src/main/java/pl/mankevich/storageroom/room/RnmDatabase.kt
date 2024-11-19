package pl.mankevich.storageroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.mankevich.storageroom.room.converter.FilterConverter
import pl.mankevich.storageroom.room.dao.CharacterRoomDao
import pl.mankevich.storageroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.storageroom.room.dao.EpisodeRoomDao
import pl.mankevich.storageroom.room.dao.LocationRoomDao
import pl.mankevich.storageroom.room.dao.RelationsRoomDao
import pl.mankevich.storageroom.room.entity.CharacterEntity
import pl.mankevich.storageroom.room.entity.CharacterPageKeyEntity
import pl.mankevich.storageroom.room.entity.EpisodeCharacterEntity
import pl.mankevich.storageroom.room.entity.EpisodeEntity
import pl.mankevich.storageroom.room.entity.LocationCharacterEntity
import pl.mankevich.storageroom.room.entity.LocationEntity

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