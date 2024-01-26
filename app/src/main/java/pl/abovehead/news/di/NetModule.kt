package pl.abovehead.news.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.abovehead.common.SuspendUseCase
import pl.abovehead.news.domain.RssItem
import pl.abovehead.news.network.FetchRssNasaFeedUseCase
import pl.abovehead.news.network.RssParser
import pl.abovehead.news.network.RssParserImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

}