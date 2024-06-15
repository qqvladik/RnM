package pl.mankevich.core.di.providers

import pl.mankevich.core.datasource.CharacterDataSource
import pl.mankevich.core.datasource.EpisodeDataSource
import pl.mankevich.core.datasource.LocationDataSource
import pl.mankevich.core.datasource.RelationsDataSource

interface StorageProvider {
    fun provideCharacterDataSource(): CharacterDataSource

    fun provideEpisodeDataSource(): EpisodeDataSource

    fun provideLocationDataSource(): LocationDataSource

    fun provideRelationsDataSource(): RelationsDataSource
}
