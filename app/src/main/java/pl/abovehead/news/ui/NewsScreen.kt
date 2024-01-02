package pl.abovehead.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import pl.abovehead.NavControllerViewModel
import pl.abovehead.databinding.ActivityNewsBinding

@Composable
fun NewsScreen( navController: NavController, navControllerViewModel: NavControllerViewModel) {
    AndroidViewBinding(ActivityNewsBinding::inflate) {
//        val newsFragment = this.fragmentContainerView.getFragment<NewsTabsFragment>()
        navControllerViewModel.storeNavController(navController)

        // ...
    }

}