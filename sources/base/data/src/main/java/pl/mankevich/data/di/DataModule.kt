package pl.mankevich.data.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.dataapi.repository.CharacterRepository
import pl.mankevich.data.repository.CharacterRepositoryImpl

@Module
interface DataModule {

    @FeatureScope
    @Binds
    fun provideCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}