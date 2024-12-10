package pl.mankevich.remoteretrofit.di

import dagger.Binds
import dagger.Module
import pl.mankevich.remoteretrofit.api.CharacterApiImpl
import pl.mankevich.remoteretrofit.api.EpisodeApiImpl
import pl.mankevich.remoteretrofit.api.LocationApiImpl
import pl.mankevich.remoteapi.api.CharacterApi
import pl.mankevich.remoteapi.api.EpisodeApi
import pl.mankevich.remoteapi.api.LocationApi

@Module
interface NetworkRetrofitApiModule {

    @Binds
    fun provideCharacterApi(impl: CharacterApiImpl): CharacterApi

    @Binds
    fun provideEpisodeApi(impl: EpisodeApiImpl): EpisodeApi

    @Binds
    fun provideLocationApi(impl: LocationApiImpl): LocationApi
}