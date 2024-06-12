package pl.abovehead.other

import android.text.util.Linkify
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.textview.MaterialTextView
import pl.abovehead.R
import pl.abovehead.common.CoilImageGetter
import pl.abovehead.common.composables.ErrorMessage
import pl.abovehead.common.composables.Loading
import pl.abovehead.other.viewModel.OtherInfoState
import pl.abovehead.other.viewModel.OtherInfoViewModel
import pl.abovehead.other.viewModel.aboutId

@Composable
fun AboutScreen() {
    val viewModel: OtherInfoViewModel = hiltViewModel()
    var offset by rememberSaveable { mutableFloatStateOf(0f) }
    val scrollState = rememberScrollState()
    val colorScheme = MaterialTheme.colorScheme
    Surface(color = colorScheme.background) {
        val state: OtherInfoState by viewModel.otherInfoState.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            viewModel.fetch(aboutId)
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
                                    setTextColor(colorScheme.onSurface.toArgb())
                                }
                            },
                            update =
                            {
                                it.text =
                                    HtmlCompat.fromHtml(
                                        s.content.trim().replace("￼", ""),
                                        HtmlCompat.FROM_HTML_MODE_COMPACT,
                                        CoilImageGetter(it),
                                        null
                                    )
                            }

                        )
                    }
                }
        }
    }
}