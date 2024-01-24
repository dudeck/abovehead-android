package pl.abovehead.pictures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.viewModel.AstroPhotoViewModel
import pl.abovehead.pictures.viewModel.PictureType
import pl.abovehead.pictures.viewModel.PicturesViewModel

@Composable
fun AstroPhotoList(addOrder: (OrderItem) -> Unit, astroPhotoViewModel: AstroPhotoViewModel) {
    val state = astroPhotoViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        astroPhotoViewModel.fetchPictures(PictureType.Astrophoto)
    }
    PictureList(addOrder = addOrder, state = state)
}