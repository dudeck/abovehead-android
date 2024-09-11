package pl.abovehead.pictures

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.ui.PicturesTabLayout

@Composable
fun GalleryScreen( addOrder: (OrderItem) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        AstroPhotoGallery(addOrder, galleryViewModel = viewModel())
    }
}
