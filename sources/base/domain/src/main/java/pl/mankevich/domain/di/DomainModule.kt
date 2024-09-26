package pl.mankevich.domain.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.domain.usecase.LoadCharacterDetailUseCaseImpl
import pl.mankevich.domain.usecase.LoadCharactersListUseCaseImpl
import pl.mankevich.domain.usecase.LoadEpisodesByCharacterIdUseCaseImpl
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase

@Module
interface DomainModule {

    @FeatureScope
    @Binds
    fun provideLoadCharactersListUseCase(impl: LoadCharactersListUseCaseImpl): LoadCharactersListUseCase

    @FeatureScope
    @Binds
    fun provideLoadCharacterDetailUseCase(impl: LoadCharacterDetailUseCaseImpl): LoadCharacterDetailUseCase

    @FeatureScope
    @Binds
    fun provideLoadEpisodesByCharacterIdUseCase(impl: LoadEpisodesByCharacterIdUseCaseImpl): LoadEpisodesByCharacterIdUseCase
}