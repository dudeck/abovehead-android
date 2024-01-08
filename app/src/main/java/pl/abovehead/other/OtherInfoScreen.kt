package pl.abovehead.other

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.abovehead.R
import pl.abovehead.pictures.ui.PicturesTabLayout

@Composable
fun OtherInfoScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text(stringResource(id = R.string.other_info))
    }
}
