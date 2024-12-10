package pl.mankevich.remoteapi.di

import pl.mankevich.remoteapi.api.CharacterApi
import pl.mankevich.remoteapi.api.EpisodeApi
import pl.mankevich.remoteapi.api.LocationApi

interface NetworkProvider {

    fun provideCharacterApi(): CharacterApi

    fun provideEpisodeApi(): EpisodeApi

    fun provideLocationApi(): LocationApi
}
