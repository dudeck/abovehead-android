package pl.abovehead.cart.screens.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class FrameSize { Small, Big }
enum class FrameColor { White, Black }
data class OrderItem(
    val title: String,
    val frameSize: FrameSize = FrameSize.Big,
    val frameColor: FrameColor = FrameColor.White,
    val image: String?,
    val addLogo: Boolean = true,
    val addTitle: Boolean = true,
)

@HiltViewModel

class OrderViewModel @Inject constructor() : ViewModel() {
    private val _orderState = MutableStateFlow<MutableList<OrderItem>>(mutableStateListOf())
    val orderState: StateFlow<List<OrderItem>> = _orderState.asStateFlow()
    fun addOrder(orderItem: OrderItem) {
        _orderState.update {
            it.apply {
                add(orderItem)
            }
        }
    }

    fun removeOrder(orderItem: OrderItem) {
        _orderState.update {
            it.apply {
                remove(orderItem)
            }
        }
    }
}