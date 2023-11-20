package pl.abovehead.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.abovehead.GetAstrophotosQuery
import pl.abovehead.R
import pl.abovehead.apolloClient

@Composable
fun PicturesScreen() {
    var responseState by rememberSaveable { mutableStateOf<GetAstrophotosQuery.Data?>(null) }
    LaunchedEffect(Unit) {
        val response = apolloClient.query(GetAstrophotosQuery("cG9zdDoxMTA=")).execute()
        Log.d("LOL", "CartScreen: ${response.data}")
        responseState = response.data!!
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        if (responseState!= null) ImagesWithTitlesList(responseState!!)
    }
}

@Composable
fun ImagesWithTitlesList(data: GetAstrophotosQuery.Data) {
    val cosik = data.page!!.blocks[1]!!.innerHtml
    val url = parseImg(cosik!!)
    Column {
        Text(stringResource(id = R.string.pictures))
        AsyncImage(
            modifier = Modifier.size(160.dp, 160.dp),
            model = url,
            contentDescription = "Mission patch"
        )

    }
}

private fun parseImg(innerHtml: String): String {
    var substr = innerHtml

    val startIndex: Int = substr.indexOf("<a href=")
    if (startIndex != -1) {
        substr = substr.substring(startIndex + 9)
        val urlIndex = substr.indexOf("\"")
        substr = substr.substring(0, urlIndex)
    }

    return substr
}