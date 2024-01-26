package pl.abovehead.news.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import pl.abovehead.common.SuspendUseCase
import pl.abovehead.news.domain.RssItem
import java.io.InputStream
import javax.inject.Inject

class FetchRssNasaFeedUseCase @Inject constructor(
    private val rssParser: RssParser,
    private val okHttpClient: OkHttpClient,
) :
    SuspendUseCase<@JvmSuppressWildcards List<RssItem>> {
    override
    suspend fun execute(): List<RssItem> {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://www.nasa.gov/rss/dyn/breaking_news.rss")
                .build()
            okHttpClient.newCall(request).execute().use {
                val inputStream: InputStream? = it.body?.byteStream()
                val parser = XmlPullParserFactory.newInstance().newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(inputStream, null)

                rssParser.parseRssItems(parser)
            }
        }
    }
}