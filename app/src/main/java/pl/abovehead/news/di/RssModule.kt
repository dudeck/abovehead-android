package pl.abovehead.news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.abovehead.common.SuspendUseCase
import pl.abovehead.news.domain.RssItem
import pl.abovehead.news.network.FetchRssNasaFeedUseCase
import pl.abovehead.news.network.RssParser
import pl.abovehead.news.network.RssParserImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RssModule {

    @Binds
    abstract fun bindRssParser(
        rssParser: RssParserImpl
    ): RssParser

    @Binds
    abstract fun bindsFetchRssNasaFeedUseCase(useCase: FetchRssNasaFeedUseCase): SuspendUseCase<List<RssItem>>

}