package pl.abovehead.pictures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.viewModel.PictureType
import pl.abovehead.pictures.viewModel.SeaViewModel

@Composable
fun SeaPhotoList(addOrder: (OrderItem) -> Unit, seaViewModel: SeaViewModel) {
    val state = seaViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        seaViewModel.fetchPictures(PictureType.Sea)
    }
    PictureList(addOrder = addOrder, state = state)
}