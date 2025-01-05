package pl.mankevich.domain.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.domain.usecase.LoadCharacterDetailUseCaseImpl
import pl.mankevich.domain.usecase.LoadCharactersByEpisodeIdUseCaseImpl
import pl.mankevich.domain.usecase.LoadCharactersByLocationIdUseCaseImpl
import pl.mankevich.domain.usecase.LoadCharactersListUseCaseImpl
import pl.mankevich.domain.usecase.LoadEpisodeDetailUseCaseImpl
import pl.mankevich.domain.usecase.LoadEpisodesByCharacterIdUseCaseImpl
import pl.mankevich.domain.usecase.LoadEpisodesListUseCaseImpl
import pl.mankevich.domain.usecase.LoadLocationDetailUseCaseImpl
import pl.mankevich.domain.usecase.LoadLocationsListUseCaseImpl
import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersByEpisodeIdUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersByLocationIdUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodeDetailUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesListUseCase
import pl.mankevich.domainapi.usecase.LoadLocationDetailUseCase
import pl.mankevich.domainapi.usecase.LoadLocationsListUseCase

@Module
interface DomainModule {

    @FeatureScope
    @Binds
    fun provideLoadCharacterDetailUseCase(impl: LoadCharacterDetailUseCaseImpl): LoadCharacterDetailUseCase

    @FeatureScope
    @Binds
    fun provideLoadCharactersByEpisodeIdUseCase(impl: LoadCharactersByEpisodeIdUseCaseImpl): LoadCharactersByEpisodeIdUseCase

    @FeatureScope
    @Binds
    fun provideLoadCharactersByLocationIdUseCase(impl: LoadCharactersByLocationIdUseCaseImpl): LoadCharactersByLocationIdUseCase

    @FeatureScope
    @Binds
    fun provideLoadCharactersListUseCase(impl: LoadCharactersListUseCaseImpl): LoadCharactersListUseCase

    @FeatureScope
    @Binds
    fun provideLoadEpisodeDetailUseCase(impl: LoadEpisodeDetailUseCaseImpl): LoadEpisodeDetailUseCase

    @FeatureScope
    @Binds
    fun provideLoadEpisodesByCharacterIdUseCase(impl: LoadEpisodesByCharacterIdUseCaseImpl): LoadEpisodesByCharacterIdUseCase

    @FeatureScope
    @Binds
    fun provideLoadEpisodesListUseCase(impl: LoadEpisodesListUseCaseImpl): LoadEpisodesListUseCase

    @FeatureScope
    @Binds
    fun provideLoadLocationDetailUseCase(impl: LoadLocationDetailUseCaseImpl): LoadLocationDetailUseCase

    @FeatureScope
    @Binds
    fun provideLoadLocationsListUseCase(impl: LoadLocationsListUseCaseImpl): LoadLocationsListUseCase
}