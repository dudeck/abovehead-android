package pl.abovehead

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.abovehead.news.network.FetchRssNasaFeedUseCase
import pl.abovehead.news.network.WallpaperRepository
import javax.inject.Inject

@HiltViewModel
class IntentViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
) : ViewModel(){
    private val _state = MutableStateFlow<Uri?>(null)
    val state: StateFlow<Uri?> = _state

    fun updateUriResult(uri: Uri) {
        _state.value = uri
    }

    suspend fun downloadWallPaper(url: String): Bitmap?{
        val bitmap = wallpaperRepository.fetch(url)
        return bitmap
    }
}