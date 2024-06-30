package pl.mankevich.network.di

import dagger.Binds
import dagger.Module
import pl.mankevich.network.datasource.CharacterNetworkDataSourceImpl
import pl.mankevich.network.datasource.EpisodeNetworkDataSourceImpl
import pl.mankevich.network.datasource.LocationNetworkDataSourceImpl
import pl.mankevich.networkapi.datasource.CharacterNetworkDataSource
import pl.mankevich.networkapi.datasource.EpisodeNetworkDataSource
import pl.mankevich.networkapi.datasource.LocationNetworkDataSource

@Module
interface NetworkDataSourceModule {

    @Binds
    fun provideCharacterNetworkDataSource(characterDataSourceImpl: CharacterNetworkDataSourceImpl): CharacterNetworkDataSource

    @Binds
    fun provideEpisodeNetworkDataSource(episodeDataSourceImpl: EpisodeNetworkDataSourceImpl): EpisodeNetworkDataSource

    @Binds
    fun provideLocationNetworkDataSource(locationDataSourceImpl: LocationNetworkDataSourceImpl): LocationNetworkDataSource
}