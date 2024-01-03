package pl.abovehead.cart.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import pl.abovehead.cart.screens.viewmodel.OrderItem
import pl.abovehead.cart.screens.viewmodel.OrderViewModel

@Composable
fun CartScreen(orderViewModel: OrderViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val ordersByState: MutableList<OrderItem> by orderViewModel.orderState.collectAsStateWithLifecycle()
        OrderList(ordersByState, orderViewModel)
    }
}

@Composable
fun OrderList(orders: List<OrderItem>, orderViewModel: OrderViewModel) {
    LazyColumn {
        items(orders.size) { index ->
            OrderItemRow(orders[index], orderViewModel = orderViewModel)
        }
    }
}

@Composable
fun OrderItemRow(order: OrderItem, orderViewModel: OrderViewModel) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clickable { /* handle click */ }
        ) {
            AsyncImage(
                model = order.image,
                contentDescription = order.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = order.title, fontWeight = FontWeight.Bold)
                Text(text = "Size: ${order.frameSize}")
                Text(text = "Color: ${order.frameColor}")
                if (order.addLogo) {
                    Text(text = "Logo: Yes")
                }
                if (order.addTitle) {
                    Text(text = "Sign: Yes")
                }
            }
            RemoveFromCartButton(order, orderViewModel = orderViewModel)
        }
    }
}

@Composable
fun RemoveFromCartButton(order: OrderItem, orderViewModel: OrderViewModel) {
    FloatingActionButton(modifier = Modifier.padding(36.dp), onClick = {
        orderViewModel.removeOrder(orderItem = order)
    }) {
        Icon(Icons.Filled.Clear, "Remove from cart")
    }
}