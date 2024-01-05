package pl.abovehead.licenses.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.abovehead.GetStaticPageQuery
import pl.abovehead.apolloClient
import pl.abovehead.licenses.viewModel.StaticPageState.ApplicationError
import pl.abovehead.licenses.viewModel.StaticPageState.Loading
import pl.abovehead.licenses.viewModel.StaticPageState.ProtocolError
import pl.abovehead.licenses.viewModel.StaticPageState.Success

private const val privacyPolicyPageId = "cG9zdDoz"

enum class StaticPageType {
    privacyPolicy
}

sealed interface StaticPageState {
    data object Loading : StaticPageState
    data class ProtocolError(val exception: ApolloException) : StaticPageState
    data class ApplicationError(val errors: List<Error>) : StaticPageState
    data class Success(val page: GetStaticPageQuery.Page) : StaticPageState
}
class StaticPageViewModel: ViewModel() {
    suspend fun fetch() {
        _pageState.value = try {
//            when()
            val response = apolloClient.query(GetStaticPageQuery(privacyPolicyPageId)).execute()
            val page = response.data?.page
            if (!response.hasErrors() && page != null) {
                Success(page)
            } else {
                ApplicationError(response.errors!!)
            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }
    }

    private val _pageState = MutableStateFlow<StaticPageState>(Loading)
    val pageState: StateFlow<StaticPageState> = _pageState.asStateFlow()
}