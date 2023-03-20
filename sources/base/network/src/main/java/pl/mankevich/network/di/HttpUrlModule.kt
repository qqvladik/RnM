package pl.mankevich.network.di

import dagger.Module
import dagger.Provides

@Module
class HttpUrlModule {

    companion object {

        private const val REST_API_URL = "http://rickandmprtyapi.com/api/" //TODO мб в конфиги какие запихать
    }

    @BaseUrl
    @Provides
    fun provideRestApiUrl(): String {
        return REST_API_URL
    }
}