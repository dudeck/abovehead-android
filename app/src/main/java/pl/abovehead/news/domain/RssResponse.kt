package pl.abovehead.news.domain

import com.google.gson.annotations.SerializedName

data class RssFeed(
    @SerializedName("channel")
    val channel: RssChannel? = null
)

// Create a data class for the RSS channel
data class RssChannel(
    @SerializedName("item")
    val items: List<RssItem2>? = null
)

// Create a data class for the RSS item
data class RssItem2(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("pubDate")
    val pubDate: String? = null
)

