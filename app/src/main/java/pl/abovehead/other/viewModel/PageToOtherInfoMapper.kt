package pl.abovehead.other.viewModel

import androidx.core.text.HtmlCompat
import pl.abovehead.GetStaticPageQuery


class PageToOtherInfoMapper {
    fun map(page: GetStaticPageQuery.Page?): String {
        var content = page?.editorBlocks?.first()?.renderedHtml ?: ""
        val startIndex = content.indexOf("<h2>")
        if (startIndex > 1) {
            content = content.substring(startIndex)
        }
        return content
    }
}