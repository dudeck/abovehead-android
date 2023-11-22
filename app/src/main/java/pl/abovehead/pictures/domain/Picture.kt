package pl.abovehead.pictures.domain

data class Picture(
    val title: String,
    val url: String?,
    val description: String,
    val shortDescription: String?
)
