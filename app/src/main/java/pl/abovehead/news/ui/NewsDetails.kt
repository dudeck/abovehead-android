package pl.abovehead.news.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.abovehead.news.domain.RssItem
import pl.abovehead.ui.theme.AboveHeadTheme

@Composable
fun NewsDetails(postId: String?) {
    val uriHandler = LocalUriHandler.current

    AboveHeadTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = item?.title ?: "")
                AsyncImage(
                    model = item?.enclosureUrl,
                    contentDescription = item?.title
                )
                Text(text = item?.description ?: "")
                Button(onClick = { uriHandler.openUri(item?.link ?: "https:www.abovehead.pl") }) {
                    Text(text = "More details")
                }
            }
        }
    }
}

