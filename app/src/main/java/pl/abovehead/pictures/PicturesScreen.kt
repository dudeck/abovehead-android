package pl.abovehead.pictures

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
import pl.abovehead.pictures.ui.PicturesTabLayout

@Composable
fun PicturesScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        PicturesTabLayout(viewModel = PicturesViewModel())
    }
}
