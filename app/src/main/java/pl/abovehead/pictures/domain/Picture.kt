package pl.abovehead.pictures.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    val title: String,
    val url: String?,
    val thumbnailUrl: String?,
    val description: String,
    val shortDescription: String?
):Parcelable
