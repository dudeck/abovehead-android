package pl.abovehead

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import pl.abovehead.news.PostsList
import pl.abovehead.news.model.RssItem
import pl.abovehead.ui.theme.AboveHeadTheme

class NewsDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", RssItem::class.java)
        } else {
            intent.getParcelableExtra("item")
        }
        Log.d("LOL", "onCreate: ${item?.title} ")
        setContent {
            AboveHeadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsDetail(item)
                }
            }
        }
    }
}

@Composable
fun NewsDetail(item: RssItem?, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = item?.title ?: "")
        AsyncImage(
            model = item?.enclosureUrl,
            contentDescription = item?.title
        )
        Text(text = item?.description ?: "")
        Button(onClick = {uriHandler.openUri(item?.link ?: "https:www.abovehead.pl") }) {
            Text(text = "More details")
        }
    }
}