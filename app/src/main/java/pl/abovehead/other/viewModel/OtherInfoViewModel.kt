package pl.abovehead.other.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.abovehead.GetStaticPageQuery
import pl.abovehead.apolloClient
import pl.abovehead.other.viewModel.OtherInfoState.ApplicationError
import pl.abovehead.other.viewModel.OtherInfoState.Loading
import pl.abovehead.other.viewModel.OtherInfoState.ProtocolError
import pl.abovehead.other.viewModel.OtherInfoState.Success
import javax.inject.Inject

const val privacyPolicyId = "cG9zdDoz"
const val aboutId = "cG9zdDoxMjU1"
const val termsOfUseId = "cG9zdDoxMjE4"

sealed interface OtherInfoState {
    data object Loading : OtherInfoState
    data class ProtocolError(val exception: ApolloException) : OtherInfoState
    data class ApplicationError(val errors: List<Error>) : OtherInfoState
    data class Success(val content: String) : OtherInfoState
}

@HiltViewModel
class OtherInfoViewModel @Inject constructor() : ViewModel() {
    suspend fun fetch(screenId: String) {
        _otherInfoState.value = try {
            val response = apolloClient.query(GetStaticPageQuery(screenId)).execute()
            val page = response.data?.page
            if (!response.hasErrors() && page?.editorBlocks?.first()?.renderedHtml?.isNotBlank() == true) {
                val info = PageToOtherInfoMapper().map(page)
                Success(info)
            } else {
                ApplicationError(response.errors!!)
            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }

    }

    private val _otherInfoState = MutableStateFlow<OtherInfoState>(Loading)
    val otherInfoState: StateFlow<OtherInfoState> = _otherInfoState.asStateFlow()
}