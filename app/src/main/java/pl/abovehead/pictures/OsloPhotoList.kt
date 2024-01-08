package pl.abovehead.pictures

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.PictureDetailsActivity
import pl.abovehead.R
import pl.abovehead.apolloClient
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.OsloPhotosState.ApplicationError
import pl.abovehead.pictures.OsloPhotosState.Loading
import pl.abovehead.pictures.OsloPhotosState.ProtocolError
import pl.abovehead.pictures.OsloPhotosState.Success
import pl.abovehead.pictures.domain.PageToPictureMapper
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.pictures.ui.AddToCartButton
import java.util.Locale

private sealed interface OsloPhotosState {
    data object Loading : OsloPhotosState
    data class ProtocolError(val exception: ApolloException) : OsloPhotosState
    data class ApplicationError(val errors: List<Error>) : OsloPhotosState
    data class Success(val pictures: List<Picture>) : OsloPhotosState
}

private const val OSLOPAGEID = "cG9zdDoxOTE="

@Composable
fun OsloPhotoList(addOrder: (OrderItem) -> Unit) {
    var state by remember { mutableStateOf<OsloPhotosState>(Loading) }
    LaunchedEffect(Unit) {
        state = try {
            val response = apolloClient.query(GetAstrophotosQuery(OSLOPAGEID)).execute()
            if (response.hasErrors()) {
                ApplicationError(response.errors!!)
            } else {
                Success(PageToPictureMapper().mapList(response.data!!.page!!))
            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }
    }
    when (val s = state) {
        Loading -> Loading()
        is ProtocolError -> ErrorMessage(
            stringResource(
                R.string.general_error_message,
            )
        )

        is ApplicationError -> ErrorMessage(s.errors[0].message)
        is Success ->
            LazyColumn {
                items(s.pictures.size) { index ->
                    if (s.pictures[index].title.isNotBlank()) PictureItem(
                        picture = s.pictures[index],
                        addOrder
                    )
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PictureItem(picture: Picture, addOrder: (OrderItem) -> Unit) {
    val mContext = LocalContext.current
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
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    //                error = painterResource(com.google.android.material.R.drawable.m3_password_eye),
                    contentDescription = "Mission patch"
                )
                AndroidView(
                    //                modifier = modifier,
                    factory = { MaterialTextView(it) },
                    update = { it.text = HtmlCompat.fromHtml(picture.shortDescription ?: "", 0) }
                )
            }
        }
        AddToCartButton(title = picture.title, image = picture.url, addOrder = addOrder)
    }
}


@Composable
private fun ErrorMessage(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}