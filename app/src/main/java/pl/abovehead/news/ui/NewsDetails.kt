package pl.abovehead.news.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
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
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    fontSize = 24.sp,
                    text = post?.title ?: ""
                )
                AsyncImage(
                    modifier = Modifier.padding (8.dp),
                    model = post?.imageUrl, contentDescription = post?.title
                )
                AndroidView(
                    modifier = Modifier.padding (8.dp),
                    factory = { MaterialTextView(it) }, update = {
                    it.text = post?.description
                })
                Button(onClick = { uriHandler.openUri(post?.link ?: "https:www.abovehead.pl") }) {
                    Text(text = "More details")
                }
            }
        }
    }
}

