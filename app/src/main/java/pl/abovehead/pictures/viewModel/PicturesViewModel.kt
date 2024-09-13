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
import javax.inject.Inject

private const val ASTROPHOTOPAGEID = "cG9zdDoxMTA="
private const val SEAPAGEID = "cG9zdDoxODY="
private const val OSLOPAGEID = "cG9zdDoxOTE="
private const val GALLERYPAGEID = "cG9zdDoxMzM2"

sealed interface PicturesState {
    data object Loading : PicturesState
    data class ProtocolError(val exception: ApolloException) : PicturesState
    data class ApplicationError(val errors: List<Error>) : PicturesState
    data class Success(val pictures: List<Picture>) : PicturesState
}

enum class PictureType {
    Astrophoto, Sea, Oslo, Gallery
}

open class PicturesViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<PicturesState>(Loading)
    val state = _state.asStateFlow()

    suspend fun fetchPictures(pictureType: PictureType) {
        if (_state.value !is Success) {
            _state.value = try {
                val response =
                    when (pictureType) {
                        PictureType.Astrophoto -> apolloClient.query(
                            GetAstrophotosQuery(
                                ASTROPHOTOPAGEID
                            )
                        ).execute()

                        PictureType.Sea -> apolloClient.query(GetAstrophotosQuery(SEAPAGEID))
                            .execute()

                        PictureType.Oslo -> apolloClient.query(GetAstrophotosQuery(OSLOPAGEID))
                            .execute()

                        PictureType.Gallery -> apolloClient.query(GetAstrophotosQuery(GALLERYPAGEID))
                            .execute()
                    }
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

    fun setErrorState(errorMessage: String) {
        _state.value = ApplicationError(
            listOf(
                com.apollographql.apollo3.api.Error(
                    message = errorMessage,
                    null,
                    null,
                    null,
                    null
                )
            )
        )
    }
}

class AstroPhotoViewModel : PicturesViewModel()
class GalleryViewModel : PicturesViewModel()
class OsloViewModel : PicturesViewModel()
class SeaViewModel : PicturesViewModel()