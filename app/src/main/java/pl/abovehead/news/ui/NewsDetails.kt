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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pl.abovehead.news.domain.Post
import pl.abovehead.news.domain.RssItem
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.ui.theme.AboveHeadTheme

@Composable
fun NewsDetails(post: Post?) {
    val uriHandler = LocalUriHandler.current

    AboveHeadTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = post?.title ?: "")
                AsyncImage(
                    model = post?.imageUrl,
                    contentDescription = post?.title
                )
                Text(text = post?.description ?: "")
                Button(onClick = { uriHandler.openUri(post?.link ?: "https:www.abovehead.pl") }) {
                    Text(text = "More details")
                }
            }
        }
    }
}

