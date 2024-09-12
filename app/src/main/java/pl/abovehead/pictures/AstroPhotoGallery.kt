package pl.abovehead.pictures

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import pl.abovehead.PictureDetailsActivity
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.common.composables.ErrorMessage
import pl.abovehead.common.composables.Loading
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.pictures.viewModel.AstroPhotoViewModel
import pl.abovehead.pictures.viewModel.GalleryViewModel
import pl.abovehead.pictures.viewModel.PictureType
import pl.abovehead.pictures.viewModel.PicturesState
import pl.abovehead.pictures.viewModel.PicturesState.ApplicationError
import pl.abovehead.pictures.viewModel.PicturesState.ProtocolError
import pl.abovehead.pictures.viewModel.PicturesState.Success

@Composable
fun AstroPhotoGallery(addOrder: (OrderItem) -> Unit, galleryViewModel: GalleryViewModel) {
    val state = galleryViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        galleryViewModel.fetchPictures(PictureType.Gallery)
    }
    GalleryView(addOrder = addOrder, state = state)
}

@Composable
fun GalleryView(addOrder: (OrderItem) -> Unit, state: State<PicturesState>) {
    when (val data = state.value) {
        PicturesState.Loading -> Loading()
        is ProtocolError -> ErrorMessage(
            stringResource(
                R.string.general_error_message,
            )
        )

        is ApplicationError -> ErrorMessage(data.errors[0].message)
        is Success -> {
            var selectedImage by remember { mutableStateOf<Picture?>(null) }
            val mContext = LocalContext.current
            if (selectedImage != null) {
                // Display full-screen image
                AsyncImage(
                    model = selectedImage!!.url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clickable {
                            val picture = selectedImage
                            selectedImage = null
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
                        } // Close on click
                )
            } else {
                // Display the gallery
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(150.dp),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    val pictures = data.pictures.filter { it.url?.isNotBlank() == true }
                    val count = pictures.size
                    items(count) { index ->
                        GalleryItem(
                            picture = pictures[index]
                        ) { selectedImage = pictures[index] }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryItem(picture: Picture, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            onClick = onClick

        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                model = picture.thumbnailUrl,
                contentDescription = "Mission patch"
            )

        }
    }
}