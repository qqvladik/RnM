package pl.mankevich.storage.di

import dagger.Binds
import dagger.Module
import pl.mankevich.storage.datasource.CharacterStorageDataSourceImpl
import pl.mankevich.storage.datasource.EpisodeStorageDataSourceImpl
import pl.mankevich.storage.datasource.LocationStorageDataSourceImpl
import pl.mankevich.storage.datasource.RelationsStorageDataSourceImpl
import pl.mankevich.storageapi.datasource.CharacterStorageDataSource
import pl.mankevich.storageapi.datasource.EpisodeStorageDataSource
import pl.mankevich.storageapi.datasource.LocationStorageDataSource
import pl.mankevich.storageapi.datasource.RelationsStorageDataSource

@Module
interface StorageDataSourceModule {

    @Binds
    fun provideCharacterStorageDataSource(characterDataSourceImpl: CharacterStorageDataSourceImpl): CharacterStorageDataSource

    @Binds
    fun provideEpisodeStorageDataSource(episodeDataSourceImpl: EpisodeStorageDataSourceImpl): EpisodeStorageDataSource

    @Binds
    fun provideLocationStorageDataSource(locationDataSourceImpl: LocationStorageDataSourceImpl): LocationStorageDataSource

    @Binds
    fun provideRelationsStorageDataSource(relationsDataSourceImpl: RelationsStorageDataSourceImpl): RelationsStorageDataSource
}