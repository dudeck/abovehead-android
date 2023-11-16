package pl.abovehead.ui.screens.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.abovehead.model.RssItem
import pl.abovehead.network.FetchRssNasaFeedUseCase
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchRssNasaFeedUseCase: FetchRssNasaFeedUseCase
) : ViewModel() {
// RssFeedViewModel.kt

    val rssItemsLiveData: MutableLiveData<List<RssItem>> = MutableLiveData()

    init {
        fetchRssFeed()
    }

    private fun fetchRssFeed() {
        viewModelScope.launch(Dispatchers.Main) {
            val rssItems = fetchRssNasaFeedUseCase.execute()

            rssItemsLiveData.value = rssItems
        }
    }
}


