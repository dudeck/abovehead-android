package pl.abovehead.news.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "item", strict=false)
data class RssFeed @JvmOverloads constructor(

    @field:ElementList(name ="item")
    @param:ElementList(name ="item")
    @field:Path("channel")
    @param:Path("channel")
    var items: List<RssItem2>? = null
)

// Create a data class for the RSS item
data class RssItem2 @JvmOverloads constructor(
    @field:Element(name ="title")
    @param:Element(name ="title")
    @field:Path("channel")
    @param:Path("channel")
    var title: String? = null,

    @field:Element(name ="link")
    @param:Element(name ="link")
    @field:Path("channel")
    @param:Path("channel")
    var link: String? = null,

    @field:Element(name ="description")
    @param:Element(name ="description")
    @field:Path("channel")
    @param:Path("channel")
    var description: String? = null,

    @field:Element(name ="pubDate")
    @param:Element(name ="pubDate")
    @field:Path("channel")
    @param:Path("channel")
    var pubDate: String? = null,
)

