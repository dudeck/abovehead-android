package pl.abovehead.news.network

import pl.abovehead.common.Repository
import pl.abovehead.news.domain.RssItem
import javax.inject.Inject

class NASARetrofitRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val rssParser: RssParser,
) :
    Repository<@JvmSuppressWildcards List<RssItem>?> {
    override suspend fun fetch(): List<RssItem> {

        val items = retrofitService.rssFeeds().body()?.items ?: emptyList()

        return items.map { element -> RssItem(
            title = element.title,
            description = element.description,
            enclosureUrl = rssParser.parseImg(null, element.content),
            link = element.link,
            pubDate = element.pubDate,
        ) }
    }
}