package pl.abovehead.news.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import pl.abovehead.common.Repository
import pl.abovehead.news.domain.RssItem
import java.io.InputStream
import javax.inject.Inject

class NASAOkHttpRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val rssParser: RssParser,
) : Repository<@JvmSuppressWildcards List<RssItem>?> {
    override suspend fun fetch(): List<RssItem> {
        return withContext(Dispatchers.IO) {
            val request =
                Request.Builder().url("https://www.nasa.gov/rss/dyn/breaking_news.rss").build()
            okHttpClient.newCall(request).execute().use { response ->
                val inputStream: InputStream? = response.body?.byteStream()
                val parser = XmlPullParserFactory.newInstance().newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(inputStream, null)
                parser?.let { rssParser.parseRssItems(it) } ?: emptyList()
            }
        }
    }
}