package pl.mankevich.databaseroom.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.withTransaction
import dagger.Module
import dagger.Provides
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.databaseroom.room.RnmDatabase
import pl.mankevich.databaseroom.room.dao.CharacterRoomDao
import pl.mankevich.databaseroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.EpisodeRoomDao
import pl.mankevich.databaseroom.room.dao.LocationRoomDao
import pl.mankevich.databaseroom.room.dao.RelationsRoomDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.databaseroom.room.dao.EpisodePageKeyRoomDao
import pl.mankevich.databaseroom.room.dao.LocationPageKeyRoomDao

@Module
class RoomModule {
    @FeatureScope
    @Provides
    fun provideDataBase(context: Context): RnmDatabase {
        return databaseBuilder(
            context,
            RnmDatabase::class.java,
            RnmDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCharacterDao(rnmDatabase: RnmDatabase): CharacterRoomDao {
        return rnmDatabase.getCharacterDao()
    }

    @Provides
    fun provideEpisodeDao(rnmDatabase: RnmDatabase): EpisodeRoomDao {
        return rnmDatabase.getEpisodeDao()
    }

    @Provides
    fun provideLocationDao(rnmDatabase: RnmDatabase): LocationRoomDao {
        return rnmDatabase.getLocationDao()
    }

    @Provides
    fun provideRelationsDao(rnmDatabase: RnmDatabase): RelationsRoomDao {
        return rnmDatabase.getRelationsDao()
    }

    @Provides
    fun provideCharacterPageKeyDao(rnmDatabase: RnmDatabase): CharacterPageKeyRoomDao {
        return rnmDatabase.getCharacterPageKeyDao()
    }

    @Provides
    fun provideEpisodePageKeyDao(rnmDatabase: RnmDatabase): EpisodePageKeyRoomDao {
        return rnmDatabase.getEpisodePageKeyDao()
    }

    @Provides
    fun provideEpisodeCharacterDao(rnmDatabase: RnmDatabase): LocationPageKeyRoomDao {
        return rnmDatabase.getEpisodeCharacterDao()
    }

    @Provides
    fun provideTransaction(rnmDatabase: RnmDatabase): Transaction {
        return object : Transaction {
            override suspend fun <R> invoke(block: suspend () -> R): R =
                rnmDatabase.withTransaction(block)
        }
    }
}
