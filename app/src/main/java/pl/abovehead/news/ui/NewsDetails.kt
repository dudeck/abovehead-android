package pl.abovehead.news.ui

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.news.domain.Post
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
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {
                Text(text = post?.title ?: "")
                AsyncImage(
                    model = post?.imageUrl,
                    contentDescription = post?.title
                )
                AndroidView(
                    factory = { MaterialTextView(it) },
                    update = {
                        it.text = post?.description
                    }
                )
                Button(onClick = { uriHandler.openUri(post?.link ?: "https:www.abovehead.pl") }) {
                    Text(text = "More details")
                }
            }
        }
    }
}

