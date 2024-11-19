package pl.mankevich.networkretrofit.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.mankevich.networkretrofit.retrofit.RnmApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val DEFAULT_OK_HTTP_TIMEOUT: Long = 30000

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create()) //TODO remove
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(DEFAULT_OK_HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_OK_HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    fun provideChuckerInterceptor(
        context: Context
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    fun provideCharactersListApi(
        retrofit: Retrofit
    ): RnmApi {
        return retrofit.create(RnmApi::class.java)
    }
}