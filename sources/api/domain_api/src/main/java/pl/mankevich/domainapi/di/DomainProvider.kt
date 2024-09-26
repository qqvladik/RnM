package pl.mankevich.domainapi.di

import pl.mankevich.domainapi.usecase.LoadCharacterDetailUseCase
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase
import pl.mankevich.domainapi.usecase.LoadEpisodesByCharacterIdUseCase

interface DomainProvider {

    fun provideLoadCharactersListUseCase(): LoadCharactersListUseCase

    fun provideLoadCharacterDetailUseCase(): LoadCharacterDetailUseCase

    fun provideLoadEpisodesByCharacterIdUseCase(): LoadEpisodesByCharacterIdUseCase
}
