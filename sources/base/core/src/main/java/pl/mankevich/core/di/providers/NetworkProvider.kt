package pl.mankevich.core.di.providers

import retrofit2.Retrofit

interface NetworkProvider {

    fun provideRetrofit(): Retrofit
}
