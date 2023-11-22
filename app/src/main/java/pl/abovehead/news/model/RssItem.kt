package pl.abovehead.news.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RssItem(
    val title: String?,
    val link: String,
    val description: String?,
    val enclosureUrl: String?,
    val pubDate: String?
) : Parcelable