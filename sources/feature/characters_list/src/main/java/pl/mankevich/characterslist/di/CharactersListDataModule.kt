package pl.mankevich.characterslist.di

import dagger.Binds
import dagger.Module
import pl.mankevich.characterslist.data.CharactersListRepositoryImpl
import pl.mankevich.characterslist.domain.CharactersListRepository
import pl.mankevich.core.di.FeatureScope

@Module
interface CharactersListDataModule {

    @FeatureScope
    @Binds
    fun provideCharactersListRepository(
        charactersListRepositoryImpl: CharactersListRepositoryImpl
    ): CharactersListRepository
}