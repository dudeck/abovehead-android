package pl.abovehead.pictures

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.PictureDetailsActivity
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.common.composables.ErrorMessage
import pl.abovehead.common.composables.Loading
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.pictures.ui.AddToCartButton
import pl.abovehead.pictures.viewModel.PicturesState
import pl.abovehead.pictures.viewModel.PicturesState.ApplicationError
import pl.abovehead.pictures.viewModel.PicturesState.Loading
import pl.abovehead.pictures.viewModel.PicturesState.ProtocolError
import pl.abovehead.pictures.viewModel.PicturesState.Success
import java.util.Locale

@Composable
fun PictureList(addOrder: (OrderItem) -> Unit, state: State<PicturesState>) {
    when (val data = state.value) {
        Loading -> Loading()
        is ProtocolError -> ErrorMessage(
            stringResource(
                R.string.general_error_message,
            )
        )

        is ApplicationError -> ErrorMessage(data.errors[0].message)
        is Success ->
            LazyColumn {
                items(data.pictures.size) { index ->
                    if (data.pictures[index].title.isNotBlank()) PictureItem(
                        picture = data.pictures[index],
                        addOrder
                    )
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureItem(picture: Picture, addOrder: (OrderItem) -> Unit) {
    val mContext = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    Box(contentAlignment = Alignment.BottomEnd) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            onClick = {
                startActivity(
                    mContext,
                    Intent(mContext, PictureDetailsActivity::class.java).apply {
                        putExtra(
                            "item",
                            picture
                        )
                    },
                    null
                )
            }

        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    text = picture.title.uppercase(Locale.ROOT)
                )
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    model = picture.url,
                    contentDescription = picture.title
                )
                AndroidView(
                    factory = { MaterialTextView(it).apply { setTextColor(colorScheme.onSurface.toArgb()) } },
                    update = { it.text = HtmlCompat.fromHtml(picture.shortDescription ?: "", 0) }
                )
            }

        }
        AddToCartButton(title = picture.title, image = picture.url, addOrder = addOrder)
    }
}