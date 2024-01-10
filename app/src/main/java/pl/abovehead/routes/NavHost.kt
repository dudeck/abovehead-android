package pl.abovehead.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.abovehead.NavControllerViewModel
import pl.abovehead.R
import pl.abovehead.cart.screens.CartScreen
import pl.abovehead.cart.screens.OrderScreen
import pl.abovehead.cart.screens.viewmodel.OrderViewModel
import pl.abovehead.news.ui.NewsDetails
import pl.abovehead.news.ui.NewsScreen
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.other.AboutScreen
import pl.abovehead.other.OtherInfoScreen
import pl.abovehead.other.PrivacyPolicyScreen
import pl.abovehead.other.TermsOfUseScreen
import pl.abovehead.pictures.PicturesScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues,

    ) {
    val navControllerViewModel = hiltViewModel<NavControllerViewModel>()
    val postViewModel = hiltViewModel<PostViewModel>()
    val orderViewModel = hiltViewModel<OrderViewModel>()
    NavHost(
        navController = navController,
        startDestination = Routes.News.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(Routes.News.route) {
            NewsScreen(
                navController = navController,
                navControllerViewModel = navControllerViewModel
            )
        }
        composable(Routes.Pictures.route) {
            PicturesScreen(orderViewModel::addOrder)
        }
        composable(
            Routes.PostDetails.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val post =
                postViewModel.findPostById(postId = backStackEntry.arguments?.getString("postId"))
            NewsDetails(post = post)
        }
        composable(Routes.ShoppingCart.route) {
            val ordersByState by orderViewModel.orderState.collectAsStateWithLifecycle()

            CartScreen(
                orders = ordersByState,
                removeOrder = { orderViewModel.removeOrder(it) },
                navController,
            )
        }
        composable(Routes.Order.route) {
            val mContext = LocalContext.current
            val policyAgreement = stringResource(R.string.i_agree_to_the_terms_of_use_and_privacy_policy)

            OrderScreen {
                startActivity(
                    mContext,
                    orderViewModel.makeOrderIntent(it, policyAgreement),
                    null
                )
            }
        }
        composable(Routes.OtherInfo.route) {
            OtherInfoScreen(navController)
        }
        composable(SettingsRoutes.TermsOfUse.route) {
            TermsOfUseScreen()
        }
        composable(SettingsRoutes.PrivacyPolicy.route) {
            PrivacyPolicyScreen()
        }
        composable(SettingsRoutes.About.route) {
            AboutScreen()
        }
    }
}
