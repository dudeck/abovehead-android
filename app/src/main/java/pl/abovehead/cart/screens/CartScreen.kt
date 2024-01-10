package pl.abovehead.cart.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderData
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.routes.Routes

@Composable
fun CartScreen(
    orders: List<OrderItem>,
    removeOrder: (OrderItem) -> Unit,
    navController: NavHostController
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        OrderList(orders, removeOrder, navController)
    }
}

@Composable
fun OrderList(
    orders: List<OrderItem>,
    removeOrder: (OrderItem) -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (orders.isEmpty()) {
            Text(text = stringResource(id = R.string.empty_cart))
        } else {
        LazyColumn(Modifier.weight(1f)) {
            items(orders.size) { index ->
                OrderItemRow(orders[index], removeOrder)
            }
        }
            Button(
                onClick = {
                    navController.navigate(Routes.Order.route)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                // Button content
                Text("Zamów", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun OrderItemRow(order: OrderItem, removeOrder: (OrderItem) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = order.image,
                contentDescription = order.title,
                modifier = Modifier
                    .size(120.dp)
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
            RemoveFromCartButton(order, removeOrder)
        }
    }
}

@Composable
fun RemoveFromCartButton(order: OrderItem, removeOrder: (OrderItem) -> Unit) {
    FloatingActionButton(modifier = Modifier.padding(16.dp), onClick = {
        removeOrder(order)
    }) {
        Icon(Icons.Filled.Clear, "Remove from cart")
    }
}