package pl.abovehead.news.di

import android.graphics.Bitmap
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.abovehead.common.Repository
import pl.abovehead.common.SuspendUseCase
import pl.abovehead.common.di.OkHttpRepository
import pl.abovehead.common.di.RetrofitRepository
import pl.abovehead.news.domain.RssItem
import pl.abovehead.news.network.FetchRssNasaFeedUseCase
import pl.abovehead.news.network.NASAOkHttpRepository
import pl.abovehead.news.network.NASARetrofitRepository
import pl.abovehead.news.network.RssParser
import pl.abovehead.news.network.RssParserImpl
import pl.abovehead.news.network.WallpaperRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RssModule {

    @Binds
    abstract fun bindRssParser(
        rssParser: RssParserImpl
    ): RssParser

    @Binds
    abstract fun bindsFetchRssNasaFeedUseCase(useCase: FetchRssNasaFeedUseCase): SuspendUseCase<List<RssItem>>

    @Binds
    @OkHttpRepository
    abstract fun bindOkHttpRepository(repository: NASAOkHttpRepository): Repository<List<RssItem>>

    @Binds
    @RetrofitRepository
    abstract fun bindRetrofitRepository(repository: NASARetrofitRepository): Repository<List<RssItem>>

    @Binds
    @RetrofitRepository
    abstract fun bindWallpaperRepository(repository: WallpaperRepository): Repository<Bitmap?>

}