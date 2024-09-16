package pl.abovehead.pictures

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.IntentViewModel

@Composable
fun GalleryScreen(intentViewModel: IntentViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        AstroPhotoGallery(
            galleryViewModel = viewModel(),
            intentViewModel = intentViewModel,
            adViewModel = hiltViewModel()
        )
    }
}
