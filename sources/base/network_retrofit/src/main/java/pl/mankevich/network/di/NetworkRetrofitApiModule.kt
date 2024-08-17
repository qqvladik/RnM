package pl.mankevich.network.di

import dagger.Binds
import dagger.Module
import pl.mankevich.network.api.CharacterApiImpl
import pl.mankevich.network.api.EpisodeApiImpl
import pl.mankevich.network.api.LocationApiImpl
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