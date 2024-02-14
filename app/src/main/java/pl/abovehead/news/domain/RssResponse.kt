package pl.abovehead.news.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(

    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    @field:Path("channel")
    @param:Path("channel")
    var items: List<RssItem2>? = null
)

// Create a data class for the RSS item
@Root(name = "item", strict = false)
data class RssItem2 @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String? = null,

    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String? = null,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    var pubDate: String? = null,

    @field:Element(name = "encoded")
    @param:Element(name = "encoded")
    var content: String? = null,

)

