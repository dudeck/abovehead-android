package pl.abovehead.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.abovehead.NavControllerViewModel
import pl.abovehead.cart.screens.CartScreen
import pl.abovehead.news.ui.NewsDetails
import pl.abovehead.news.ui.NewsScreen
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.pictures.PicturesScreen

@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues, navControllerViewModel: NavControllerViewModel, postViewModel: PostViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.News.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(Routes.News.route) {
            NewsScreen( navController = navController, navControllerViewModel = navControllerViewModel)
        }
        composable(Routes.Pictures.route) {
            PicturesScreen()
        }
        composable(
            Routes.PostDetails.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val post = postViewModel.findPostById(postId =backStackEntry.arguments?.getString("postId") )
            NewsDetails(post = post )
        }
        composable(Routes.ShoppingCart.route) {
            CartScreen()
        }
    }
}
