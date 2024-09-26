package pl.mankevich.data.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.data.repository.CharacterRepositoryImpl
import pl.mankevich.data.repository.EpisodeRepositoryImpl
import pl.mankevich.data.repository.LocationRepositoryImpl
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.dataapi.repository.EpisodeRepository
import pl.mankevich.dataapi.repository.LocationRepository

@Module
interface DataModule {

    @FeatureScope
    @Binds
    fun provideCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @FeatureScope
    @Binds
    fun provideEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @FeatureScope
    @Binds
    fun provideLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}