package pl.mankevich.domain.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.domain.usecase.LoadCharactersListUseCaseImpl
import pl.mankevich.domainapi.usecase.LoadCharactersListUseCase

@Module
interface DomainModule {

    @FeatureScope
    @Binds
    fun provideLoadCharactersListUseCase(impl: LoadCharactersListUseCaseImpl): LoadCharactersListUseCase
}