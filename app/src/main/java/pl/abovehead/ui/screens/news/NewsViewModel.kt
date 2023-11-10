package pl.abovehead.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
) : ViewModel() {

    init {
        viewModelScope.launch { fetchData() }
    }

    private suspend fun fetchData() = withContext(Dispatchers.IO) {
        val reader = RSSReader()
        reader.fetchData()
    }

}