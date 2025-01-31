package pl.abovehead.news.network

import pl.abovehead.common.Repository
import pl.abovehead.common.SuspendUseCase
import pl.abovehead.common.di.RetrofitRepository
import pl.abovehead.news.domain.RssItem
import javax.inject.Inject

class FetchRssNasaFeedUseCase @Inject constructor(
    @RetrofitRepository private val nasaRepository: Repository<@JvmSuppressWildcards List<RssItem>>,
) : SuspendUseCase<@JvmSuppressWildcards List<RssItem>> {
    override suspend fun execute(): List<RssItem> {
        return nasaRepository.fetch("https://www.nasa.gov/rss/dyn/breaking_news.rss")
    }
}