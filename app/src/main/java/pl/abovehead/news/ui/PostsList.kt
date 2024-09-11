package pl.abovehead.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import pl.abovehead.R
import pl.abovehead.common.composables.ErrorMessage
import pl.abovehead.common.composables.Loading
import pl.abovehead.news.domain.Post
import pl.abovehead.news.viewModel.PostViewModel
import pl.abovehead.news.viewModel.PostsState
import pl.abovehead.news.viewModel.PostsState.ApplicationError
import pl.abovehead.news.viewModel.PostsState.Loading
import pl.abovehead.news.viewModel.PostsState.ProtocolError
import pl.abovehead.news.viewModel.PostsState.Success
import pl.abovehead.routes.Routes
import java.util.Locale

@Composable
fun PostsList(postViewModel: PostViewModel, navController: NavController) {
    val state: PostsState by postViewModel.postsState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        postViewModel.fetch()
    }
    when (val s = state) {
        Loading -> Loading()
        is ProtocolError -> ErrorMessage(
            stringResource(
                R.string.general_error_message,
            )
        )

        is ApplicationError -> ErrorMessage(s.errors[0].message)
        is Success ->
            LazyColumn {
                items(s.posts.size) { index ->
                    PostItem(post = s.posts[index], navController)
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostItem(post: Post, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(400.dp)
            .fillMaxSize(),
        onClick = {
            navController.navigate(Routes.PostDetails.route + "/${post.id}")
        }

    ) {
        Box (modifier = Modifier.fillMaxSize()){
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                model = post.imageUrl,
                //                placeholder = painterResource(R.drawable.ic_launcher_background),
                //                error = painterResource(com.google.android.material.R.drawable.m3_password_eye),
                contentDescription = "Mission patch"
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(colorResource(id = R.color.md_theme_surfaceContainer_highContrast)),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color.White,
                    text = post.title.uppercase(Locale.ROOT)
                )
                Spacer(modifier = Modifier.size(height = 16.dp, width = 8.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(colorResource(id = R.color.md_theme_surfaceContainer_highContrast)),
                    color = colorResource(id = R.color.md_theme_onSurfaceVariant_highContrast),
                    text = post.modifiedDate.toString()
                )

            }
        }
    }
}