package pl.abovehead.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import pl.abovehead.SuspendUseCase
import pl.abovehead.model.RssItem
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class FetchRssNasaFeedUseCase @Inject constructor(private val rssParser: RssParser) :
    SuspendUseCase {
    override
    suspend fun execute(): List<RssItem> {
        return withContext(Dispatchers.IO) {
            val rssFeedUrl =
                "https://www.nasa.gov/rss/dyn/breaking_news.rss" // Replace with your RSS feed URL
            val url = URL(rssFeedUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            try {
                val inputStream: InputStream = connection.inputStream
                val parser = XmlPullParserFactory.newInstance().newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(inputStream, null)

                rssParser.parseRssItems(parser)
            } finally {
                connection.disconnect()
            }
        }
    }
}