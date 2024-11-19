package pl.mankevich.storageroom.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.withTransaction
import dagger.Module
import dagger.Provides
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.storageroom.room.RnmDatabase
import pl.mankevich.storageroom.room.dao.CharacterRoomDao
import pl.mankevich.storageroom.room.dao.CharacterPageKeyRoomDao
import pl.mankevich.storageroom.room.dao.EpisodeRoomDao
import pl.mankevich.storageroom.room.dao.LocationRoomDao
import pl.mankevich.storageroom.room.dao.RelationsRoomDao
import pl.mankevich.storageapi.dao.Transaction

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
    fun provideTransaction(rnmDatabase: RnmDatabase): Transaction {
        return object : Transaction {
            override suspend fun <R> invoke(block: suspend () -> R): R =
                rnmDatabase.withTransaction(block)
        }
    }
}
