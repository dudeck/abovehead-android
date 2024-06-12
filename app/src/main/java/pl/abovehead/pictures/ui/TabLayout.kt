package pl.abovehead.pictures.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.AstroPhotoList
import pl.abovehead.pictures.OsloPhotoList
import pl.abovehead.pictures.SeaPhotoList
import pl.abovehead.pictures.viewModel.AstroPhotoViewModel
import pl.abovehead.pictures.viewModel.OsloViewModel
import pl.abovehead.pictures.viewModel.PicturesTabViewModel
import pl.abovehead.pictures.viewModel.PicturesViewModel
import pl.abovehead.pictures.viewModel.SeaViewModel

@Composable
fun PicturesTabLayout(
    addOrder: (OrderItem) -> Unit,
    astroPhotoViewModel: AstroPhotoViewModel = viewModel(),
    seaViewModel: SeaViewModel = viewModel(),
    osloViewModel: OsloViewModel = viewModel()
) {
    val picturesTabViewModel: PicturesTabViewModel = viewModel()
    val tabIndex = picturesTabViewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            picturesTabViewModel.tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { picturesTabViewModel.updateTabIndex(index) },
                )
            }
        }

        when (tabIndex.value) {
            0 -> AstroPhotoList(addOrder, astroPhotoViewModel)
            1 -> SeaPhotoList(addOrder, seaViewModel)
            2 -> OsloPhotoList(addOrder, osloViewModel)
        }
    }
}

@Composable
fun AddToCartButton(addOrder: (OrderItem) -> Unit, title: String, image: String?) {
   val context = LocalContext.current
    FloatingActionButton(modifier = Modifier.padding(36.dp), onClick = {
        Toast.makeText(context, R.string.added_to_cart, Toast.LENGTH_SHORT).show()
        addOrder(OrderItem(title = title, image = image))
    }) {
        Icon(Icons.Filled.Add, stringResource(R.string.add_to_cart))
    }
}