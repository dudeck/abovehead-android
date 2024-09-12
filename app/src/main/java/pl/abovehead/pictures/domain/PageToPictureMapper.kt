package pl.abovehead.pictures.domain

import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.common.urlFileExists

const val THUMBNAIL_SUFFIX1 = "-1536x864.jpg"
const val THUMBNAIL_SUFFIX2 = "-864x1536.jpg"
class PageToPictureMapper {

    private fun splitListByDelimiter(inputList: List<GetAstrophotosQuery.Block?>): List<List<GetAstrophotosQuery.Block?>> =
        inputList.fold(mutableListOf(mutableListOf<GetAstrophotosQuery.Block?>())) { acc, item ->
            if (item?.innerHtml?.contains("<a href") == true) {
                acc.add(mutableListOf(item))
            } else {
                acc.lastOrNull()?.add(item)
            }
            acc
        }

    private fun parseImg(innerHtml: String?): String {
        if (innerHtml == null) return ""
        var substr = innerHtml

        val startIndex: Int = substr.indexOf("<a href=")
        if (startIndex != -1) {
            substr = substr.substring(startIndex + 9)
            val urlIndex = substr.indexOf("\"")
            substr = substr.substring(0, urlIndex)
        }

        return substr
    }

    suspend fun mapList(page: GetAstrophotosQuery.Page): List<Picture> {
        val list = page.blocks.drop(1)
        val elements = splitListByDelimiter(list)

        val pictures = mutableListOf<Picture>() + elements.map { item ->
            val url =
                parseImg(item.firstOrNull { it?.tagName == "p" && it.innerHtml?.contains("<a href") == true }?.innerHtml)

            val thumbnailUrl1 = url.replace(".jpg", THUMBNAIL_SUFFIX1)
            val thumbnailUrl2 = url.replace(".jpg", THUMBNAIL_SUFFIX2)
            Picture(
                title = item.firstOrNull { it?.tagName == "h2" }?.innerHtml ?: "",
                shortDescription = item.firstOrNull { it?.tagName == "ul" }?.innerHtml
                    ?: "",
                description = item.filter { it?.tagName == "p" && it.innerHtml?.contains("<a href") == false }
                    .fold("") { acc, element -> acc + element?.innerHtml },
                url = url,
                thumbnailUrl = if (urlFileExists(thumbnailUrl1)) thumbnailUrl1 else thumbnailUrl2,
            )
        }

        return pictures
    }
}