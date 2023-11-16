package pl.abovehead.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.abovehead.SuspendUseCase
import pl.abovehead.network.FetchRssNasaFeedUseCase
import pl.abovehead.network.RssParser
import pl.abovehead.network.RssParserImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RssModule {

    @Binds
    abstract fun bindRssParser(
        rssParser: RssParserImpl
    ): RssParser

    @Binds
    abstract fun bindsFetchRssNasaFeedUseCase(useCase: FetchRssNasaFeedUseCase): SuspendUseCase

}