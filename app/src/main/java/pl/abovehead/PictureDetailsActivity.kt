package pl.abovehead

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.ui.theme.AboveHeadTheme
import java.util.Locale

class PictureDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", Picture::class.java)
        } else {
            intent.getParcelableExtra("item")
        }
        setContent {
            AboveHeadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PictureDetails(item)
                }
            }
        }
    }
}

@Composable
fun PictureDetails(item: Picture?, modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            text = item?.title?.uppercase(Locale.ROOT) ?: ""
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            model = item?.url ?: "",
            contentDescription = item?.title
        )
        AndroidView(
            modifier = modifier,
            factory = { MaterialTextView(it).apply { setTextColor(colorScheme.onSurface.toArgb()) } },
            update = { it.text = HtmlCompat.fromHtml(item?.shortDescription ?: "", 0) }
        )
        Text(text = item?.description ?: "")
    }
}