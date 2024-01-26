package pl.abovehead.news.network

import pl.abovehead.news.domain.RssFeed
import pl.abovehead.news.domain.RssItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {
    @Headers("Content-Type: application/rss+xml")
    @GET("rss/dyn/breaking_news.rss")
    suspend fun rssFeeds(): Response<RssFeed>
}