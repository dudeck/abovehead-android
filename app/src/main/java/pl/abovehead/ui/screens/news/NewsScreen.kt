package pl.abovehead.ui.screens.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import pl.abovehead.databinding.ActivityMainBinding

@Composable
fun NewsScreen() {
    AndroidViewBinding(ActivityMainBinding::inflate) {
        val newsFragment = fragmentContainerView.getFragment<NewsFragment>()
        // ...
    }

}