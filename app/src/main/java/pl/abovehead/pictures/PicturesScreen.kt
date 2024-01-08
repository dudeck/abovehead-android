package pl.abovehead.pictures

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.ui.PicturesTabLayout

@Composable
fun PicturesScreen( addOrder: (OrderItem) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        PicturesTabLayout(addOrder)
    }
}
