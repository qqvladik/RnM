package pl.mankevich.domainapi.di

import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase

interface DomainProvider {

    fun provideLoadCharactersListUseCase(): LoadCharactersListUseCase
}
