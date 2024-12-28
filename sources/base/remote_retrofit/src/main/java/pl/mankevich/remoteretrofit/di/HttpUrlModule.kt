package pl.mankevich.remoteretrofit.di

import dagger.Module
import dagger.Provides
import pl.mankevich.core.API_URL

@Module
class HttpUrlModule {

    @BaseUrl
    @Provides
    fun provideRestApiUrl(): String {
        return API_URL.plus("/")
    }
}