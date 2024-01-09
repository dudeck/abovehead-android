package pl.abovehead.other

import android.text.util.Linkify
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.R
import pl.abovehead.other.viewModel.OtherInfoState
import pl.abovehead.other.viewModel.OtherInfoViewModel

@Composable
fun PrivacyPolicyScreen() {
    val viewModel: OtherInfoViewModel = hiltViewModel()
    var offset by rememberSaveable { mutableFloatStateOf(0f) }
    val scrollState = rememberScrollState()
    Surface(color = MaterialTheme.colorScheme.background) {
        val state: OtherInfoState by viewModel.otherInfoState.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            viewModel.fetch()
        }
        when (val s = state) {
            OtherInfoState.Loading -> Loading()
            is OtherInfoState.ProtocolError -> ErrorMessage(
                stringResource(
                    R.string.general_error_message,
                )
            )

            is OtherInfoState.ApplicationError -> ErrorMessage(s.errors[0].message)
            is OtherInfoState.Success ->
                Box(
                    Modifier
                        .scrollable(
                            orientation = Orientation.Vertical,
                            // Scrollable state: describes how to consume
                            // scrolling delta and update offset
                            state = rememberScrollableState { delta ->
                                offset += delta
                                delta
                            }
                        )
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 8.dp)
                            .verticalScroll(scrollState)
                    ) {
                        AndroidView(
                            //                modifier = modifier,
                            factory = {
                                MaterialTextView(it).apply {
                                    // links
                                    autoLinkMask = Linkify.WEB_URLS
                                    linksClickable = true
                                }
                            },
                            update =
                            {
                                it.text =
                                    HtmlCompat.fromHtml(s.content.trim().replace("￼", "") ?: "", 0)
                            }

                        )
                    }
                }

        }
    }

}


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