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
import pl.abovehead.cart.screens.domain.OrderData
import pl.abovehead.cart.screens.domain.OrderDataMapper
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.cart.screens.domain.OrderMapper
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderMapper: OrderMapper,
    private val orderDataMapper: OrderDataMapper
) : ViewModel() {
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

    fun updateOrder(oldItem: OrderItem, newItem: OrderItem) {
        _orderState.update {
            it.apply {
                this[indexOf(oldItem)] = newItem
            }
        }
    }

    fun makeOrderIntent(orderData: OrderData, policyAgreement: String): Intent {
        val orders = orderMapper.mapOrder(_orderState.value) + "\n" + policyAgreement
        val userData = orderDataMapper.mapData(orderData)

        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.setData(Uri.parse("mailto:"))

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("zamowienia@abovehead.pl"))
            putExtra(Intent.EXTRA_SUBJECT, "Zamówienie - Android")
            putExtra(Intent.EXTRA_TEXT, userData + orders)
        }
        emailIntent.selector = selectorIntent
        return emailIntent
    }
}