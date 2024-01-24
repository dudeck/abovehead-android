package pl.abovehead.pictures.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.apolloClient
import pl.abovehead.pictures.domain.PageToPictureMapper
import pl.abovehead.pictures.domain.Picture
import pl.abovehead.pictures.viewModel.PicturesState.ApplicationError
import pl.abovehead.pictures.viewModel.PicturesState.Loading
import pl.abovehead.pictures.viewModel.PicturesState.ProtocolError
import pl.abovehead.pictures.viewModel.PicturesState.Success

private const val ASTROPHOTOPAGEID = "cG9zdDoxMTA="

sealed interface PicturesState {
    data object Loading : PicturesState
    data class ProtocolError(val exception: ApolloException) : PicturesState
    data class ApplicationError(val errors: List<Error>) : PicturesState
    data class Success(val pictures: List<Picture>) : PicturesState
}

class PicturesViewModel : ViewModel() {
    private val _state = MutableStateFlow<PicturesState>(Loading)
    val state = _state.asStateFlow()

    suspend fun fetchPictures() {
        if (_state.value !is Success) {
            _state.value = try {
                val response = apolloClient.query(GetAstrophotosQuery(ASTROPHOTOPAGEID)).execute()
                if (response.hasErrors()) {
                    ApplicationError(response.errors!!)
                } else {
                    Success(PageToPictureMapper().mapList(response.data!!.page!!))
                }
            } catch (e: ApolloException) {
                ProtocolError(e)
            }
        }
    }

}