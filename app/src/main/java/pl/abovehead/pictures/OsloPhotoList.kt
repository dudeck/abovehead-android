package pl.abovehead.pictures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.viewModel.OsloViewModel
import pl.abovehead.pictures.viewModel.PictureType

@Composable
fun OsloPhotoList(addOrder: (OrderItem) -> Unit, osloViewModel: OsloViewModel) {
    val state = osloViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        osloViewModel.fetchPictures(PictureType.Oslo)
    }
    PictureList(addOrder = addOrder, state = state)
}