package pl.mankevich.storageapi.di

import pl.mankevich.storageapi.datasource.CharacterStorageDataSource
import pl.mankevich.storageapi.datasource.EpisodeStorageDataSource
import pl.mankevich.storageapi.datasource.LocationStorageDataSource
import pl.mankevich.storageapi.datasource.RelationsStorageDataSource

interface StorageProvider {

    fun provideCharacterStorageDataSource(): CharacterStorageDataSource

    fun provideEpisodeStorageDataSource(): EpisodeStorageDataSource

    fun provideLocationStorageDataSource(): LocationStorageDataSource

    fun provideRelationsStorageDataSource(): RelationsStorageDataSource
}
