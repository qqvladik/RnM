package pl.mankevich.databaseroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.mankevich.databaseroom.room.converter.CharacterFilterConverter
import pl.mankevich.databaseroom.room.converter.EpisodeFilterConverter
import pl.mankevich.databaseroom.room.converter.LocationFilterConverter
import pl.mankevich.databaseroom.room.dao.CharacterRoomDao
import pl.mankevich.databaseroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.EpisodePageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.EpisodeRoomDao
import pl.mankevich.databaseroom.room.dao.LocationPageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.LocationRoomDao
import pl.mankevich.databaseroom.room.dao.RelationsRoomDao
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodeCharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodePageKeyRoomEntity
import pl.mankevich.databaseroom.room.entity.EpisodeRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationCharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationPageKeyRoomEntity
import pl.mankevich.databaseroom.room.entity.LocationRoomEntity

@Database(
    entities = [
        CharacterRoomEntity::class,
        EpisodeRoomEntity::class,
        LocationRoomEntity::class,
        EpisodeCharacterRoomEntity::class,
        LocationCharacterRoomEntity::class,
        CharacterPageKeyRoomEntity::class,
        EpisodePageKeyRoomEntity::class,
        LocationPageKeyRoomEntity::class
    ],
    version = RnmDatabase.DATABASE_VERSION
)
@TypeConverters(
    CharacterFilterConverter::class,
    LocationFilterConverter::class,
    EpisodeFilterConverter::class
)
abstract class RnmDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "rnm_database"
    }

    abstract fun getCharacterDao(): CharacterRoomDao

    abstract fun getEpisodeDao(): EpisodeRoomDao

    abstract fun getLocationDao(): LocationRoomDao

    abstract fun getRelationsDao(): RelationsRoomDao

    abstract fun getCharacterPageKeyDao(): CharacterPageKeyRoomDao

    abstract fun getEpisodePageKeyDao(): EpisodePageKeyRoomDao

    abstract fun getEpisodeCharacterDao(): LocationPageKeyRoomDao
}