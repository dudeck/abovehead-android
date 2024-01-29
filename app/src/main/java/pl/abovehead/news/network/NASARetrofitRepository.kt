package pl.abovehead.news.network

import pl.abovehead.common.Repository
import pl.abovehead.news.domain.RssItem
import javax.inject.Inject

class NASARetrofitRepository @Inject constructor(private val retrofitService: RetrofitService) :
    Repository<@JvmSuppressWildcards List<RssItem>?> {
    override suspend fun fetch(): List<RssItem>? {

        val items = retrofitService.rssFeeds().body()?.items
        return emptyList()
    }
}