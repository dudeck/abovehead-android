package pl.abovehead.common.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.abovehead.NavControllerViewModel
import pl.abovehead.common.NavBarItem
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.routes.NavigationHost
import pl.abovehead.routes.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(viewModel: NavControllerViewModel, postViewModel: PostViewModel) {
    val navController = rememberNavController()
    val navBarStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        NavigationHost(
            navController = navController,
            paddingValues = paddingValues,
            navControllerViewModel = viewModel,
            postViewModel = postViewModel
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        Routes.News,
        Routes.Pictures,
        Routes.ShoppingCart,
    )
    val navBarStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBarStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {
            NavBarItem().bottomNavBarItems().forEachIndexed { _, navigationItem ->
                NavigationBarItem(selected = navigationItem.route == currentDestination?.route,
                    onClick = {
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navigationItem.icon, contentDescription = stringResource(
                                id = navigationItem.contentDescriptionId
                            )
                        )
                    },
                    label = {
                        Text(stringResource(id = navigationItem.titleId))
                    })
            }
        }
    }
}