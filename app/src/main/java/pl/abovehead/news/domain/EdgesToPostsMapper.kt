package pl.abovehead.news.domain

import androidx.core.text.HtmlCompat
import pl.abovehead.GetPostsQuery
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EdgesToPostsMapper {
    fun mapList(edges: List<GetPostsQuery.Edge?>): List<Post> {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val postList = mutableListOf<Post>() + edges.filterNotNull().map { item ->
            Post(
                id = item.node?.id ?: "",
                title = item.node?.title ?: "",
                description = HtmlCompat.fromHtml(item.node?.content ?: "", 0).toString().trim().replace("￼", ""),
                imageUrl = item.node?.featuredImage?.node?.mediaItemUrl ?: "",
                link = item.node?.link ?: "",
                modifiedDate = if ((item.node?.modified)?.isBlank() == false) LocalDate.parse(
                    item.node.modified,
                    inputFormatter
                ) else null
            )
        }

        return postList.sortedByDescending { it.modifiedDate }
    }
}