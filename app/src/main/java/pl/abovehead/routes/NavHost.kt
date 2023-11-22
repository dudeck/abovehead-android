package pl.abovehead.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.abovehead.cart.screens.CartScreen
import pl.abovehead.news.NewsScreen
import pl.abovehead.pictures.PicturesScreen

@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Routes.News.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(Routes.News.route) {
            NewsScreen()
        }
        composable(Routes.Pictures.route) {
            PicturesScreen()
        }
        composable(Routes.ShoppingCart.route) {
            CartScreen()
        }
    }
}
