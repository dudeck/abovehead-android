package pl.abovehead.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.R
import pl.abovehead.apolloClient

@Composable
fun PicturesScreen() {
    var responseState by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) {
        val response = apolloClient.query(GetAstrophotosQuery("cG9zdDoxMTA=")).execute()
        Log.d("LOL", "CartScreen: ${response.data}")
        responseState.plus(response)
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Text(stringResource(id = R.string.pictures))
    }
}

//@Composable
//fun ImagesWithTitlesList(){
//    LazyColumn {
//        items() {
//            AsyncImage(
//                modifier = Modifier.size(160.dp, 160.dp),
//                model = data.launch?.mission?.missionPatch,
//                contentDescription = "Mission patch"
//            )
//        }
//    }
//}