package pl.mankevich.dataapi.di

import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.dataapi.repository.LocationRepository

interface DataProvider {

    fun provideCharacterRepository(): CharacterRepository

    fun provideEpisodeRepository(): EpisodeRepository

    fun provideLocationRepository(): LocationRepository
}
