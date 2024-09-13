package pl.abovehead

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IntentViewModel @Inject constructor(
) : ViewModel(){
    private val _state = MutableStateFlow<Uri?>(null)
    val state: StateFlow<Uri?> = _state

    fun updateUriResult(uri: Uri) {
        _state.value = uri
    }
}