package pl.abovehead.pictures.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PicturesTabViewModel @Inject constructor() : ViewModel() {

    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Astrofoto", "Morze", "Oslo")

    fun updateTabIndex(i: Int) {
        _tabIndex.value = i
    }

}