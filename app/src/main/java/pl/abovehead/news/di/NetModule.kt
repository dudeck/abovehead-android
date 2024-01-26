package pl.abovehead.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.abovehead.news.network.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.nasa.gov/")
            .client(okHttpClient)
            .addConverterFactory(JaxbConverterFactory.create())
            .build();
    }


    @Provides
    @Singleton
    fun provideRetrofitService(
        retrofit: Retrofit
    ): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}