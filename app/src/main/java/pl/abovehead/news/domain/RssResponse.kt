package pl.abovehead.news.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(

    @field:Element(name = "channel")
    @param:Element(name = "channel")
    @field:Path("rss")
    @param:Path("rss")
    var channel: Channel? = null
) {

}

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(

    @field:ElementList(name = "item")
    @param:ElementList(name = "item")
    @field:Path("channel")
    @param:Path("channel")
    var items: List<RssItem2>? = null
)

// Create a data class for the RSS item
@Root(name = "item", strict = false)
data class RssItem2 @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    @field:Path("channel")
    @param:Path("channel")
    var title: String? = null,

    @field:Element(name = "link")
    @param:Element(name = "link")
    @field:Path("channel")
    @param:Path("channel")
    var link: String? = null,

    @field:Element(name = "description")
    @param:Element(name = "description")
    @field:Path("channel")
    @param:Path("channel")
    var description: String? = null,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    @field:Path("channel")
    @param:Path("channel")
    var pubDate: String? = null,
)

