package pl.abovehead.pictures

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import pl.abovehead.IntentViewModel
import pl.abovehead.R
import pl.abovehead.common.AdViewModel
import pl.abovehead.common.composables.ErrorMessage
import pl.abovehead.common.composables.Loading
import pl.abovehead.common.saveBitmapToMediaStore
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.pictures.viewModel.GalleryViewModel
import pl.abovehead.pictures.viewModel.PictureType
import pl.abovehead.pictures.viewModel.PicturesState
import pl.abovehead.pictures.viewModel.PicturesState.ApplicationError
import pl.abovehead.pictures.viewModel.PicturesState.ProtocolError
import pl.abovehead.pictures.viewModel.PicturesState.Success

@Composable
fun AstroPhotoGallery(
    galleryViewModel: GalleryViewModel,
    intentViewModel: IntentViewModel,
    adViewModel: AdViewModel
) {
    val state = galleryViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        galleryViewModel.fetchPictures(PictureType.Gallery)
    }
    GalleryView(state = state, intentViewModel, adViewModel)
}

@Composable
fun GalleryView(
    state: State<PicturesState>,
    intentViewModel: IntentViewModel,
    adViewModel: AdViewModel
) {

    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    val mContext = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                adViewModel.getAdView(context) // Get the AdView from the ViewModel
            },
            update = { adView ->
                // Update the AdView if needed
            }
        )
        when (val data = state.value) {
            PicturesState.Loading -> Loading()
            is ProtocolError -> ErrorMessage(
                stringResource(
                    R.string.general_error_message,
                )
            )

            is ApplicationError -> ErrorMessage(data.errors[0].message)
            is Success -> {
                var selectedImage by rememberSaveable { mutableStateOf<Picture?>(null) }
                var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

                if (selectedImage != null) {
                    // Display full-screen image
                    Box {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                            //            .aspectRatio(1280f / 959f)
                        ) {
                            val transformableState =
                                rememberTransformableState { zoomChange, panChange, rotationChange ->
                                    scale = (scale * zoomChange).coerceIn(1f, 5f)

                                    val extraWidth = (scale - 1) * constraints.maxWidth
                                    val extraHeight = (scale - 1) * constraints.maxHeight

                                    val maxX = extraWidth / 2
                                    val maxY = extraHeight / 2

                                    offset = Offset(
                                        x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                                        y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
                                    )
                                }
                            AsyncImage(
                                model = selectedImage!!.url,
                                contentDescription = null,
                                onSuccess = { _ ->
                                    coroutineScope.launch {
                                        val bitmap =
                                            selectedImage!!.url?.let {
                                                intentViewModel.downloadWallPaper(
                                                    url = it
                                                )
                                            }
                                        imageUri =
                                            bitmap?.let {
                                                saveBitmapToMediaStore(
                                                    mContext,
                                                    it,
                                                    selectedImage!!.title
                                                )
                                            }

                                    }
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                        translationX = offset.x
                                        translationY = offset.y
                                    }
                                    .transformable(transformableState)
                                    .clickable {
                                        selectedImage = null
                                        scale = 1f
                                        offset = Offset.Zero
                                    } //Close on click
                            )
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(Icons.Filled.Close,
                                    "Close gallery item view",
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.background, CircleShape
                                        )
                                        .padding(8.dp)
                                        .clickable {
                                            selectedImage = null
                                            scale = 1f
                                            offset = Offset.Zero
                                        })
                            }
                        }
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(vertical = 48.dp, horizontal = 16.dp),
                            onClick = {
                                imageUri?.let {
                                    intentViewModel.updateUriResult(it)
                                    openPictureViaIntent(mContext, it)
                                }
                            },
                        ) {
                            Icon(Icons.Filled.Save, "Floating action button.")
                        }
                    }

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

private fun openPictureViaIntent(
    context: Context,
    imageUri: Uri,
) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(imageUri, "image/*")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, intent, null)
//    val wallpaperManager = WallpaperManager.getInstance(context)
//    try {
//        val isWallpaperSet = wallpaperManager.isSetWallpaperAllowed
//        val mimeType = context.contentResolver.getType(imageUri)
//        Log.d("LOL", "setWallpaperFromUri: isWallpaperSet= $isWallpaperSet")
//        Log.d("LOL", "setWallpaperFromUri: imageUri schema= ${imageUri.scheme}")
//        Log.d("LOL", "setWallpaperFromUri: mimeType= ${mimeType}")
//        wallpaperManager.getCropAndSetWallpaperIntent(imageUri).also { intent ->
//            startActivity(context, intent, null)
//        }
//    } catch (e: Exception) {
//        Log.d("LOL", "setWallpaperFromUri: exc= ${e.message}")
////        galleryViewModel.setErrorState(
////            e.message ?: context.getString(R.string.wallpaper_setup_error)
////        )
//        wallpaperManager.setBitmap(bitmap)
//    }
}