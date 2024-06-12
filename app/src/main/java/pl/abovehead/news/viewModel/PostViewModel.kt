package pl.abovehead.news.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.abovehead.GetPostsQuery
import pl.abovehead.analytics.AnalyticsService
import pl.abovehead.apolloClient
import pl.abovehead.news.domain.EdgesToPostsMapper
import pl.abovehead.news.domain.Post
import pl.abovehead.news.viewModel.PostsState.ApplicationError
import pl.abovehead.news.viewModel.PostsState.Loading
import pl.abovehead.news.viewModel.PostsState.ProtocolError
import pl.abovehead.news.viewModel.PostsState.Success
import javax.inject.Inject

sealed interface PostsState {
    data object Loading : PostsState
    data class ProtocolError(val exception: ApolloException) : PostsState
    data class ApplicationError(val errors: List<Error>) : PostsState
    data class Success(val posts: List<Post>) : PostsState
}
@HiltViewModel
class PostViewModel @Inject constructor(private val analyticsService: AnalyticsService) :
    ViewModel() {
    suspend fun fetch() {
        _postsState.value = try {
            val response = apolloClient.query(GetPostsQuery(first = Optional.present(100))).execute()
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

    fun findPostById(postId: String?): Post? {
        val state = _postsState.value
        if (state is Success) {
            analyticsService.logBlogPostDetailsAction(postId ?: "-1")
            return state.posts.firstOrNull { it.id == postId }
        }
        return null
    }

    private val _postsState = MutableStateFlow<PostsState>(Loading)
    val postsState: StateFlow<PostsState> = _postsState.asStateFlow()
}