package pl.abovehead.news.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Post(
    val id : String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val modifiedDate: LocalDate?
):Parcelable
