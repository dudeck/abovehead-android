package pl.abovehead.pictures

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.IntentViewModel
import pl.abovehead.cart.screens.domain.OrderItem

@Composable
fun GalleryScreen(intentViewModel: IntentViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        AstroPhotoGallery(galleryViewModel = viewModel(), intentViewModel = intentViewModel)
    }
}
