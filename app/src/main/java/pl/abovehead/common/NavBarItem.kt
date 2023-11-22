package pl.abovehead.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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
                titleId = R.string.pictures,
                icon = Icons.Filled.AccountBox,
                contentDescriptionId = R.string.pictures,
                route = Routes.Pictures.route,
            ),
            NavBarItem(
                titleId = R.string.shoppingCart,
                icon = Icons.Filled.Check,
                contentDescriptionId = R.string.cart,
                route = Routes.ShoppingCart.route,
            ),
        )
    }
}
