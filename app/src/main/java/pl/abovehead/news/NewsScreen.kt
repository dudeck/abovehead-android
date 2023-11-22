package pl.abovehead.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import pl.abovehead.databinding.ActivityNewsBinding

@Composable
fun NewsScreen() {
    AndroidViewBinding(ActivityNewsBinding::inflate) {
//        val newsFragment = this.fragmentContainerView.getFragment<NewsFragment>()

        // ...
    }

}