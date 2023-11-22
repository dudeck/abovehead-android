package pl.abovehead.pictures.domain

import pl.abovehead.GetAstrophotosQuery


class PageToPictureMapper {

    private fun splitListByDelimiter(inputList: List<GetAstrophotosQuery.Block?>): List<List<GetAstrophotosQuery.Block?>> =
        inputList.fold(mutableListOf(mutableListOf<GetAstrophotosQuery.Block?>())) { acc, item ->
            if (item?.tagName == "h2") {
                acc.add(mutableListOf(item))
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
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

    fun mapList(page: GetAstrophotosQuery.Page): List<Picture> {
        val list = page.blocks.drop(2)
        val elements = splitListByDelimiter(list)

        val pictures = mutableListOf<Picture>() + elements.map { item ->
            Picture(
                title = item.firstOrNull { it?.tagName == "h2" }?.innerHtml ?: "",
                shortDescription = item.firstOrNull { it?.tagName == "ul" }?.innerHtml
                    ?: "",
                description = item.filter { it?.tagName == "p" && it.innerHtml?.contains("<a href") == false }
                    .fold("") { acc, element -> acc + element?.innerHtml },
                url = parseImg(item.firstOrNull { it?.tagName == "p" && it.innerHtml?.contains("<a href") == true }?.innerHtml)
            )
        }

        return pictures
    }
}