package pl.abovehead.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import pl.abovehead.NavControllerViewModel
import pl.abovehead.news.viewModel.PostViewModel

@AndroidEntryPoint
class BlogNewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navControllerViewModel: NavControllerViewModel =
                    ViewModelProvider(requireActivity())[NavControllerViewModel::class.java]

                PostsList(
                    postViewModel =   ViewModelProvider(requireActivity())[PostViewModel::class.java],
                    navControllerViewModel.navControllerState.value
                )
            }
        }
    }
}