@file:OptIn(ExperimentalMaterial3Api::class)

package pl.abovehead

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import pl.abovehead.cart.screens.viewmodel.OrderViewModel
import pl.abovehead.common.composables.BottomNavigation
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.ui.theme.AboveHeadTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navControllerViewModel: NavControllerViewModel =
            ViewModelProvider(this)[NavControllerViewModel::class.java]
        val postViewModel: PostViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        val orderViewModel: OrderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]


        setContent {
            AboveHeadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigation(navControllerViewModel, postViewModel, orderViewModel)
                }
            }
        }
    }
}