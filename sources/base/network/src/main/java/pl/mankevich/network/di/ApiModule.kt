package pl.mankevich.network.di

import dagger.Module
import dagger.Provides
import pl.mankevich.network.api.RnmApi
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideCharactersListApi(
        retrofit: Retrofit
    ): RnmApi {
        return retrofit.create(RnmApi::class.java)
    }
}
