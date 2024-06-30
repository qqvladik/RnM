package pl.mankevich.networkapi.di

import pl.mankevich.networkapi.datasource.CharacterNetworkDataSource
import pl.mankevich.networkapi.datasource.EpisodeNetworkDataSource
import pl.mankevich.networkapi.datasource.LocationNetworkDataSource

interface NetworkProvider {

    fun provideCharacterNetworkDataSource(): CharacterNetworkDataSource

    fun provideEpisodeNetworkDataSource(): EpisodeNetworkDataSource

    fun provideLocationNetworkDataSource(): LocationNetworkDataSource
}
