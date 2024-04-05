package pl.abovehead.news.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.abovehead.analytics.AnalyticsService
import pl.abovehead.news.domain.RssItem
import pl.abovehead.news.network.FetchRssNasaFeedUseCase
import javax.inject.Inject


@HiltViewModel
class NASANewsViewModel @Inject constructor(
    private val fetchRssNasaFeedUseCase: FetchRssNasaFeedUseCase,
    private val analyticsService: AnalyticsService
) : ViewModel() {
// RssFeedViewModel.kt

    val rssItemsLiveData: MutableLiveData<List<RssItem>> = MutableLiveData()

    init {
        fetchRssFeed()
    }

    private fun fetchRssFeed() {
        viewModelScope.launch(Dispatchers.Main) {
            runCatching {
                val rssItems = fetchRssNasaFeedUseCase.execute()

                rssItemsLiveData.value = rssItems
            }.onFailure {
                analyticsService.logNASAFeedFetchError(it.message ?: "general error")
            }.onSuccess {
                analyticsService.logNASAFeedsFetched()
            }
        }
    }
}


