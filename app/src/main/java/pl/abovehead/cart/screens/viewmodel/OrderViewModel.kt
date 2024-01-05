package pl.abovehead.cart.screens.viewmodel

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.cart.screens.domain.OrderMapper
import javax.inject.Inject


@HiltViewModel
class OrderViewModel(@Inject val orderMapper: OrderMapper) : ViewModel() {
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

    fun makeOrderIntent(email: String): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_TEXT, orderMapper.mapOrder(_orderState.value) + "\nWyrażam zgodę na przetwarzanie moich danych zgodnie z Polityką Prywatności.")
            data = Uri.parse("mailto:zamowienia@abovehead.pl")
        }
    }
}