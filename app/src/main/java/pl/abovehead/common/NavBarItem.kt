package pl.abovehead.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import pl.abovehead.R
import pl.abovehead.routes.Routes

data class NavBarItem(
    val titleId: Int = 0,
    val icon: ImageVector = Icons.Filled.Home,
    val contentDescriptionId: Int = 0,
    val route: String = ""
) {
    fun bottomNavBarItems(): List<NavBarItem> {
        return listOf(
            NavBarItem(
                titleId = R.string.news,
                icon = Icons.Filled.List,
                contentDescriptionId = R.string.news,
                route = Routes.News.route,
            ),
            NavBarItem(
                titleId = R.string.gallery,
                icon = Icons.Filled.Face,
                contentDescriptionId = R.string.gallery,
                route = Routes.Gallery.route,
            ),
            NavBarItem(
                titleId = R.string.pictures,
                icon = Icons.Filled.AccountBox,
                contentDescriptionId = R.string.pictures,
                route = Routes.Pictures.route,
            ),
            NavBarItem(
                titleId = R.string.shoppingCart,
                icon = Icons.Filled.ShoppingCart,
                contentDescriptionId = R.string.cart,
                route = Routes.ShoppingCart.route,
            ),
            NavBarItem(
                titleId = R.string.other_info,
                icon = Icons.Filled.Info,
                contentDescriptionId = R.string.other_info,
                route = Routes.OtherInfo.route,
            ),
        )
    }
}
