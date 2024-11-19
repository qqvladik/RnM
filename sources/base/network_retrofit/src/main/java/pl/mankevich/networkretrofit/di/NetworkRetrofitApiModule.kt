package pl.mankevich.networkretrofit.di

import dagger.Binds
import dagger.Module
import pl.mankevich.networkretrofit.api.CharacterApiImpl
import pl.mankevich.networkretrofit.api.EpisodeApiImpl
import pl.mankevich.networkretrofit.api.LocationApiImpl
import pl.mankevich.networkapi.api.CharacterApi
import pl.mankevich.networkapi.api.EpisodeApi
import pl.mankevich.networkapi.api.LocationApi

@Module
interface NetworkRetrofitApiModule {

    @Binds
    fun provideCharacterApi(impl: CharacterApiImpl): CharacterApi

    @Binds
    fun provideEpisodeApi(impl: EpisodeApiImpl): EpisodeApi

    @Binds
    fun provideLocationApi(impl: LocationApiImpl): LocationApi
}