package pl.abovehead.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.abovehead.R

@Composable
fun NewsScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text(stringResource(id = R.string.news))
    }
}