package pl.abovehead.cart.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.FrameColor
import pl.abovehead.cart.screens.domain.FrameSize
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.cart.screens.domain.translateFrameColorToString
import pl.abovehead.cart.screens.viewmodel.OrderViewModel
import pl.abovehead.routes.Routes

@Composable
fun CartScreen(
    orders: List<OrderItem>,
    removeOrder: (OrderItem) -> Unit,
    updateOrder: (OrderItem, OrderItem) -> Unit,
    navController: NavHostController
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        OrderList(orders, removeOrder, updateOrder, navController)
    }
}

@Composable
fun OrderList(
    orders: List<OrderItem>,
    removeOrder: (OrderItem) -> Unit,
    updateOrder: (OrderItem, OrderItem) -> Unit,
    navController: NavHostController
) {
    val orderVM: OrderViewModel = hiltViewModel()
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
                    OrderItemRow(orders[index], removeOrder, updateOrder)
                }
            }
            Button(
                onClick = {
                    orderVM.logOrderButtonClickEvent()
                    navController.navigate(Routes.Order.route)
                }, modifier = Modifier.padding(8.dp)
            ) {
                // Button content
                Text(stringResource(R.string.order), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun OrderItemRow(
    order: OrderItem, removeOrder: (OrderItem) -> Unit, updateOrder: (OrderItem, OrderItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = order.image,
                contentDescription = order.title,
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.size(36.dp))
                Text(
                    text = order.title.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
                RemoveFromCartButton(order, removeOrder)
            }
            SizeAndColorOptions(order, updateOrder)
        }
    }
}

@Composable
fun SizeAndColorOptions(currentOrderItem: OrderItem, updateOrder: (OrderItem, OrderItem) -> Unit) {
    var size by remember { mutableStateOf(currentOrderItem.frameSize) }
    var color by remember { mutableStateOf(currentOrderItem.frameColor) }

    val resources = LocalContext.current.resources

    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(stringResource(id = R.string.color))
            Button(
                onClick = {
                    color = FrameColor.White
                    updateOrder(
                        currentOrderItem, currentOrderItem.copy(frameColor = color)
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (color == FrameColor.White) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White)
                        .size(8.dp)
                )

                Text(
                    text = translateFrameColorToString(FrameColor.White, resources),
                    Modifier.padding(start = 10.dp)
                )
            }
            Button(
                onClick = {
                    color = FrameColor.Black
                    updateOrder(
                        currentOrderItem, currentOrderItem.copy(frameColor = color)
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (color == FrameColor.Black) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .size(8.dp)
                )

                Text(
                    text = translateFrameColorToString(FrameColor.Black, resources),
                    Modifier.padding(start = 10.dp)
                )
            }
        }
        Column {


            Text(stringResource(id = R.string.size))
            Button(
                onClick = {
                    size = FrameSize.Big
                    updateOrder(
                        currentOrderItem, currentOrderItem.copy(frameSize = size)
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (size == FrameSize.Big) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = FrameSize.Big.string, Modifier.padding(start = 10.dp)
                )
            }
            Button(
                onClick = {
                    size = FrameSize.Small
                    updateOrder(
                        currentOrderItem, currentOrderItem.copy(frameSize = size)
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (size == FrameSize.Small) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = FrameSize.Small.string, Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
fun RemoveFromCartButton(order: OrderItem, removeOrder: (OrderItem) -> Unit) {
    IconButton(modifier = Modifier.size(36.dp), onClick = {
        removeOrder(order)
    }, content = { Icon(Icons.Filled.Delete, "Remove from cart") })
}

class SampleOrderItemProvider : PreviewParameterProvider<OrderItem> {
    override val values = sequenceOf(
        OrderItem(
            "Title",
            FrameSize.Big,
            FrameColor.White,
            "https://abovehead.pl/wp-content/uploads/2023/11/Ameryka-30x40-wiz.jpg",
            true,
            true
        )
    )
}

@Preview
@Composable
fun OrderItemPreview(@PreviewParameter(SampleOrderItemProvider::class) order: OrderItem) {
    OrderItemRow(order = order, removeOrder = {}, updateOrder = { _, _ -> })
}