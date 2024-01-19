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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.FrameColor
import pl.abovehead.cart.screens.domain.FrameSize
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.cart.screens.domain.translateFrameColorToString
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
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = order.image,
                contentDescription = order.title,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Column(Modifier.weight(1f)) {
                Text(text = order.title.uppercase(), fontWeight = FontWeight.Bold)
                SizeAndColorOptions(order, updateOrder)
            }
            RemoveFromCartButton(order, removeOrder)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeAndColorOptions(currentOrderItem: OrderItem, updateOrder: (OrderItem, OrderItem) -> Unit) {
    var size by remember { mutableStateOf(currentOrderItem.frameSize) }
    var color by remember { mutableStateOf(currentOrderItem.frameColor) }

    val resources = LocalContext.current.resources

    Column(
        modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(id = R.string.color))
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    color = FrameColor.White
                    updateOrder(
                        currentOrderItem, currentOrderItem.copy(frameColor = color)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (color == FrameColor.White)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primaryContainer
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
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (color == FrameColor.Black)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primaryContainer
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
        Text(stringResource(id = R.string.size))
        Button(
            onClick = {
                size = FrameSize.Big
                updateOrder(
                    currentOrderItem, currentOrderItem.copy(frameSize = size)
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                if (size == FrameSize.Big)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = FrameSize.Big.string, Modifier.padding(start = 10.dp)
            )
        }
        Button(onClick = {
            size = FrameSize.Small
            updateOrder(
                currentOrderItem, currentOrderItem.copy(frameSize = size)
            )
        },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                if (size == FrameSize.Small)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = FrameSize.Small.string, Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun RemoveFromCartButton(order: OrderItem, removeOrder: (OrderItem) -> Unit) {
    IconButton(onClick = {
        removeOrder(order)
    }, content = { Icon(Icons.Filled.Clear, "Remove from cart") })
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