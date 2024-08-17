package pl.mankevich.network.di

import dagger.Module
import dagger.Provides

@Module
class HttpUrlModule {

    companion object {

        //TODO мб в конфиги какие запихать для того, чтобы этот модуль можно было и в других прилах юзать а базовую урлу
        // задавать через конфиги. Но если нужно будет добавлять свои интерсепторы то можно сделать также отдельный
        // модуль для своих интерсепторов
        private const val REST_API_URL = "https://rickandmortyapi.com/api/"
    }

    @BaseUrl
    @Provides
    fun provideRestApiUrl(): String {
        return REST_API_URL
    }
}