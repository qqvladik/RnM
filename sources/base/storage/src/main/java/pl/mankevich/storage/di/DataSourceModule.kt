package pl.mankevich.storage.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.datasource.CharacterDataSource
import pl.mankevich.core.datasource.EpisodeDataSource
import pl.mankevich.core.datasource.LocationDataSource
import pl.mankevich.core.datasource.RelationsDataSource
import pl.mankevich.storage.db.datasource.CharacterDataSourceImpl
import pl.mankevich.storage.db.datasource.EpisodeDataSourceImpl
import pl.mankevich.storage.db.datasource.LocationDataSourceImpl
import pl.mankevich.storage.db.datasource.RelationsDataSourceImpl

@Module
interface DataSourceModule {

    @Binds
    fun provideCharacterDataSource(characterDataSourceImpl: CharacterDataSourceImpl): CharacterDataSource

    @Binds
    fun provideEpisodeDataSource(episodeDataSourceImpl: EpisodeDataSourceImpl): EpisodeDataSource

    @Binds
    fun provideLocationDataSource(locationDataSourceImpl: LocationDataSourceImpl): LocationDataSource

    @Binds
    fun provideRelationsDataSource(relationsDataSourceImpl: RelationsDataSourceImpl): RelationsDataSource
}