package pl.abovehead.cart.screens.viewmodel

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import pl.abovehead.cart.screens.SampleOrderItemProvider
import pl.abovehead.cart.screens.domain.OrderData
import pl.abovehead.cart.screens.domain.OrderDataMapper
import pl.abovehead.cart.screens.domain.OrderMapper

class OrderViewModelTest {
    private val viewModel = OrderViewModel(OrderMapper(), OrderDataMapper())

    private val orderItem = SampleOrderItemProvider().values.first()


    @Test
    fun `orderViewModel getOrderState isEmpty`() = runTest {
        val value = viewModel.orderState.value

        assertTrue(value.isEmpty())
    }

    @Test
    fun `orderViewModel AddOrder OrderState returns order  `() {
        viewModel.addOrder(orderItem = orderItem)

        val value = viewModel.orderState.value
        assert(value.size == 1)
        assertEquals(orderItem, value.first())
    }

    @Test
    fun `orderViewModel RemoveOrder OrderState is empty`() {
        viewModel.addOrder(orderItem = orderItem)

        viewModel.removeOrder(orderItem)

        val value = viewModel.orderState.value
        assertTrue(value.isEmpty())
    }

    @Test
    fun `orderViewModel updateOrder`() {
        viewModel.addOrder(orderItem = orderItem)

        viewModel.updateOrder(orderItem, orderItem.copy(title = "Title2"))

        val value = viewModel.orderState.value
        assert(value.size == 1)
        assertEquals("Title2", value.first().title)
    }

    @Test
    @Ignore
    fun orderViewModel_makeOrderIntent() {
        viewModel.addOrder(orderItem = orderItem)
//        val value = viewModel.orderState.value
        val res = mock<Resources>()
        `when` (res.getString(anyInt())).thenReturn("resourceString")
        val policyAgreement = "Policy"
        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk()

        val intent = viewModel.makeOrderIntent(
            OrderData(
                "name",
                "surname",
                "phoneNumber",
                "promoCode",
                true,
                addTitle = true
            ), policyAgreement, res
        )

        assertEquals(Intent.ACTION_SEND, intent.action)
    }
}