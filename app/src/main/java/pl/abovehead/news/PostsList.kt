package pl.abovehead.news

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import pl.abovehead.GetPostsQuery
import pl.abovehead.PictureDetailsActivity
import pl.abovehead.R
import pl.abovehead.apolloClient
import pl.abovehead.news.PostsState.ApplicationError
import pl.abovehead.news.PostsState.Loading
import pl.abovehead.news.PostsState.ProtocolError
import pl.abovehead.news.PostsState.Success
import pl.abovehead.news.domain.Post
import pl.abovehead.news.domain.EdgesToPostsMapper
import java.util.Locale

private sealed interface PostsState {
    data object Loading : PostsState
    data class ProtocolError(val exception: ApolloException) : PostsState
    data class ApplicationError(val errors: List<Error>) : PostsState
    data class Success(val posts: List<Post>) : PostsState
}

@Composable
fun PostsList() {
    var state by remember { mutableStateOf<PostsState>(Loading) }
    LaunchedEffect(Unit) {
        state = try {
            val response = apolloClient.query(GetPostsQuery()).execute()
            val edges = response.data?.posts?.edges
            if (!response.hasErrors() && edges?.isNotEmpty() == true) {
                val posts = EdgesToPostsMapper().mapList(edges = edges)
                Success(posts)
            } else {
                ApplicationError(response.errors!!)


            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }
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
                    PostItem(post = s.posts[index])

                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostItem(post: Post) {
    val mContext = LocalContext.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        onClick = {
            startActivity(
                mContext,
                Intent(mContext, PictureDetailsActivity::class.java).apply {
                    putExtra(
                        "item",
                        post
                    )
                },
                null
            )
        }

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                text = post.title.uppercase(Locale.ROOT)
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                model = post.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_background),
//                error = painterResource(com.google.android.material.R.drawable.m3_password_eye),
                contentDescription = "Mission patch"
            )
            Text(text = post.modifiedDate.toString())
        }

    }
}


@Composable
private fun ErrorMessage(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}