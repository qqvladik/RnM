package pl.mankevich.networkapi.di

import pl.mankevich.networkapi.api.CharacterApi
import pl.mankevich.networkapi.api.EpisodeApi
import pl.mankevich.networkapi.api.LocationApi

interface NetworkProvider {

    fun provideCharacterApi(): CharacterApi

    fun provideEpisodeApi(): EpisodeApi

    fun provideLocationApi(): LocationApi
}
