package pl.abovehead.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.abovehead.news.model.RssItem
import pl.abovehead.news.network.FetchBlogPostsFeedUseCase
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val fetchBlogPostsFeedUseCase: FetchBlogPostsFeedUseCase
) : ViewModel() {
// RssFeedViewModel.kt

    val rssItemsLiveData: MutableLiveData<List<RssItem>> = MutableLiveData()

    init {
        fetchRssFeed()
    }

    private fun fetchRssFeed() {
        viewModelScope.launch(Dispatchers.Main) {
            val rssItems = fetchBlogPostsFeedUseCase.execute()

            rssItemsLiveData.value = rssItems
        }
    }
}


