package pl.abovehead.pictures

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.apolloClient
import pl.abovehead.pictures.AstroPhotosState.ApplicationError
import pl.abovehead.pictures.AstroPhotosState.Loading
import pl.abovehead.pictures.AstroPhotosState.ProtocolError
import pl.abovehead.pictures.AstroPhotosState.Success
import pl.abovehead.pictures.domain.PageToPictureMapper

private sealed interface AstroPhotosState {
    data object Loading : AstroPhotosState
    data class ProtocolError(val exception: ApolloException) : AstroPhotosState
    data class ApplicationError(val errors: List<Error>) : AstroPhotosState
    data class Success(val data: GetAstrophotosQuery.Data) : AstroPhotosState
}

private const val ASTROPHOTOPAGEID = "cG9zdDoxMTA="

@Composable
fun AstroPhotoList() {
    var state by remember { mutableStateOf<AstroPhotosState>(Loading) }
    LaunchedEffect(Unit) {
        state = try {
            val response = apolloClient.query(GetAstrophotosQuery(ASTROPHOTOPAGEID)).execute()
            if (response.hasErrors()) {
                ApplicationError(response.errors!!)
            } else {
                PageToPictureMapper().mapList(response.data!!.page!!)
                Success(response.data!!)
            }
        } catch (e: ApolloException) {
            ProtocolError(e)
        }
    }
    when (val s = state) {
        Loading -> Loading()
        is ProtocolError -> ErrorMessage("Oh no... A protocol error happened: ${s.exception.message}")
        is ApplicationError -> ErrorMessage(s.errors[0].message)
        is Success -> Text(text = "fgf")
//            LazyColumn {
//                items(s.data) { launch ->
//                    PictureItem(launch = launch, onClick = onLaunchClick)
//                }
//
//                item {
//                    if (response?.data?.launches?.hasMore == true) {
//                        LoadingItem()
//                        cursor = response?.data?.launches?.cursor
//                    }
//                }
//            }
    }
}


//@Composable
//private fun PictureItem(launch: GetAstrophotosQuery.Data, onClick: (launchId: String) -> Unit) {
//    ListItem(
//        modifier = Modifier.clickable { onClick(launch.id) },
//        headlineText = {
//            // Mission name
//            Text(text = launch.mission?.name ?: "")
//        },
//        supportingText = {
//            // Site
//            Text(text = launch.site ?: "")
//        },
//        leadingContent = {
//            // Mission patch
//            AsyncImage(
//                modifier = Modifier.size(68.dp, 68.dp),
//                model = launch.mission?.missionPatch,
//                placeholder = painterResource(R.drawable.ic_placeholder),
//                error = painterResource(R.drawable.ic_placeholder),
//                contentDescription = "Mission patch"
//            )
//        }
//    )
//}


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