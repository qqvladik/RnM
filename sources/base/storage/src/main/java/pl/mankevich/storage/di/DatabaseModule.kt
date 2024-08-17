package pl.mankevich.storage.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.withTransaction
import dagger.Module
import dagger.Provides
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.storage.db.RnmDatabase
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.dao.CharacterPageKeyDao
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.dao.RelationsDao
import pl.mankevich.storageapi.datasource.Transaction

@Module
class DatabaseModule {
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
    fun provideCharacterDao(rnmDatabase: RnmDatabase): CharacterDao {
        return rnmDatabase.getCharacterDao()
    }

    @Provides
    fun provideEpisodeDao(rnmDatabase: RnmDatabase): EpisodeDao {
        return rnmDatabase.getEpisodeDao()
    }

    @Provides
    fun provideLocationDao(rnmDatabase: RnmDatabase): LocationDao {
        return rnmDatabase.getLocationDao()
    }

    @Provides
    fun provideRelationsDao(rnmDatabase: RnmDatabase): RelationsDao {
        return rnmDatabase.getRelationsDao()
    }

    @Provides
    fun provideCharacterPageKeyDao(rnmDatabase: RnmDatabase): CharacterPageKeyDao {
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
