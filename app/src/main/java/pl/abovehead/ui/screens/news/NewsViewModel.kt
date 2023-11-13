package pl.abovehead.ui.screens.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import pl.abovehead.model.RssItem
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
) : ViewModel() {
// RssFeedViewModel.kt

    val rssItemsLiveData: MutableLiveData<List<RssItem>> = MutableLiveData()

    init {
        fetchRssFeed()
    }

    private fun fetchRssFeed() {
        viewModelScope.launch(Dispatchers.Main) {
            val rssItems = withContext(Dispatchers.IO) {
                val rssFeedUrl =
                    "https://www.nasa.gov/rss/dyn/breaking_news.rss" // Replace with your RSS feed URL
                val url = URL(rssFeedUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

                try {
                    val inputStream: InputStream = connection.inputStream
                    val parser = XmlPullParserFactory.newInstance().newPullParser()
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                    parser.setInput(inputStream, null)

                    parseRssItems(parser)
                } finally {
                    connection.disconnect()
                }
            }

            rssItemsLiveData.value = rssItems
        }
    }

    private fun parseRssItems(parser: XmlPullParser): List<RssItem> {
        val rssItems = mutableListOf<RssItem>()
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name.equals("item", ignoreCase = true)) {
                        val item = parseRssItem(parser)
                        rssItems.add(item)
                    }
                }
            }
            eventType = parser.next()
        }

        return rssItems
    }

    private fun parseRssItem(parser: XmlPullParser): RssItem {
        var title = ""
        var description = ""
        var link = ""
        var imageUrl = ""
        var eventType = parser.eventType

        if (eventType == XmlPullParser.START_DOCUMENT) {
            print("LOL")
        }

        while (!(eventType == XmlPullParser.END_TAG && parser.name.equals(
                "item",
                ignoreCase = true
            ))
        ) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when {
                        parser.name.equals("title", ignoreCase = true) -> {
                            title = parseText(parser)
                        }

                        parser.name.equals("description", ignoreCase = true) -> {
                            description = parseText(parser)
                        }

                        parser.name.equals("link", ignoreCase = true) -> {
                            link = parseText(parser)
                        }

                        parser.name.equals("content:encoded", ignoreCase = true) -> {
                            imageUrl = parseImg(parser)
                        }
                        // Add more tags as needed
                    }
                }
            }
            eventType = parser.next()
        }

        return RssItem(title, description, link, imageUrl)
    }

    private fun parseText(parser: XmlPullParser): String {
        var result = ""
        var eventType = parser.next()

        while (eventType != XmlPullParser.END_TAG) {
            if (eventType == XmlPullParser.TEXT) {
                result = parser.text
            }
            eventType = parser.next()
        }

        return result
    }

    private fun parseImg(parser: XmlPullParser): String {
        var substr = parseText(parser)

        val startIndex: Int = substr.indexOf("<img")
        if (startIndex != -1) {
            substr = substr.substring(startIndex)
            val srcIndex = substr.indexOf("src=\"")
            if (srcIndex != -1) {
                substr = substr.substring(srcIndex+5)
                val urlIndex = substr.indexOf("\"")
                substr = substr.substring(0, urlIndex)
            }
        }

        return substr
    }


}


