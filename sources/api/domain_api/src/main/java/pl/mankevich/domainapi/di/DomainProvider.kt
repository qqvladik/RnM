package pl.mankevich.domainapi.di

import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersByEpisodeIdUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersByLocationIdUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodeDetailUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesListUseCase
import pl.mankevich.domainapi.usecase.LoadLocationDetailUseCase
import pl.mankevich.domainapi.usecase.LoadLocationsListUseCase

interface DomainProvider {

    fun provideLoadCharacterDetailUseCase(): LoadCharacterDetailUseCase

    fun provideLoadCharactersByEpisodeIdUseCase(): LoadCharactersByEpisodeIdUseCase

    fun provideLoadCharactersByLocationIdUseCase(): LoadCharactersByLocationIdUseCase

    fun provideLoadCharactersListUseCase(): LoadCharactersListUseCase

    fun provideLoadEpisodeDetailUseCase(): LoadEpisodeDetailUseCase

    fun provideLoadEpisodesByCharacterIdUseCase(): LoadEpisodesByCharacterIdUseCase

    fun provideLoadEpisodesListUseCase(): LoadEpisodesListUseCase

    fun provideLoadLocationDetailUseCase(): LoadLocationDetailUseCase

    fun provideLoadLocationsListUseCase(): LoadLocationsListUseCase
}
