package pl.abovehead.pictures.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.abovehead.cart.screens.viewmodel.OrderViewModel
import pl.abovehead.pictures.AstroPhotoList
import pl.abovehead.pictures.OsloPhotoList
import pl.abovehead.pictures.PicturesViewModel
import pl.abovehead.pictures.SeaPhotoList

@Composable
fun PicturesTabLayout(orderViewModel: OrderViewModel) {
    val picturesViewModel:PicturesViewModel = viewModel()
    val tabIndex = picturesViewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            picturesViewModel.tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { picturesViewModel.updateTabIndex(index) },
                )
            }
        }

        when (tabIndex.value) {
            0 -> AstroPhotoList(orderViewModel)
            1 -> SeaPhotoList()
            2 -> OsloPhotoList()
        }
    }
}