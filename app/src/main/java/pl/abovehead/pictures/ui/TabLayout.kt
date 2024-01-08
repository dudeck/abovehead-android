package pl.abovehead.pictures.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.AstroPhotoList
import pl.abovehead.pictures.OsloPhotoList
import pl.abovehead.pictures.PicturesViewModel
import pl.abovehead.pictures.SeaPhotoList

@Composable
fun PicturesTabLayout(addOrder: (OrderItem) -> Unit) {
    val picturesViewModel: PicturesViewModel = viewModel()
    val tabIndex = picturesViewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            picturesViewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { picturesViewModel.updateTabIndex(index) },
                )
            }
        }

        when (tabIndex.value) {
            0 -> AstroPhotoList(addOrder)
            1 -> SeaPhotoList(addOrder)
            2 -> OsloPhotoList(addOrder)
        }
    }
}

@Composable
fun AddToCartButton(addOrder: (OrderItem) -> Unit, title: String, image: String?) {
    FloatingActionButton(modifier = Modifier.padding(36.dp), onClick = {
        addOrder(OrderItem(title = title, image = image))
    }) {
        Icon(Icons.Filled.Add, "Add to cart")
    }
}