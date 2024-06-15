package pl.mankevich.storage.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import pl.mankevich.core.di.qualifiers.ApplicationContext
import pl.mankevich.storage.db.RnmDatabase
import pl.mankevich.storage.db.dao.CharacterDao
import pl.mankevich.storage.db.dao.EpisodeDao
import pl.mankevich.storage.db.dao.LocationDao
import pl.mankevich.storage.db.dao.RelationsDao

@Module
class DatabaseModule {
    @StorageScope
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RnmDatabase {
        return databaseBuilder(
            context,
            RnmDatabase::class.java,
            RnmDatabase.DATABASE_NAME
        ).build()
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
}
