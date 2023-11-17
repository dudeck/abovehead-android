package pl.abovehead.network

import org.xmlpull.v1.XmlPullParser
import pl.abovehead.model.RssItem
import javax.inject.Inject

interface RssParser{
    fun parseRssItems(parser: XmlPullParser): MutableList<RssItem>
}
class RssParserImpl @Inject constructor(): RssParser {
    override
    fun parseRssItems(parser: XmlPullParser): MutableList<RssItem> {
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
        var pubDate = ""

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

                        parser.name.equals("pubDate", ignoreCase = true) -> {
                            pubDate = parseText(parser)
                        }
                        // Add more tags as needed
                    }
                }
            }
            eventType = parser.next()
        }

        return RssItem(title, link, description, imageUrl, pubDate)
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
                substr = substr.substring(srcIndex + 5)
                val urlIndex = substr.indexOf("\"")
                substr = substr.substring(0, urlIndex)
            }
        }

        return substr
    }


}