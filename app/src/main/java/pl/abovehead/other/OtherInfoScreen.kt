package pl.abovehead.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.abovehead.R
import pl.abovehead.routes.SettingsRoutes

@Composable
fun OtherInfoScreen(navController: NavController) {
    SettingsList(settings = settings, navController)
}

val settings = listOf(SettingsRoutes.PrivacyPolicy, SettingsRoutes.TermsOfUse, SettingsRoutes.About)

@Composable
fun SettingsList(settings: List<SettingsRoutes>, navController: NavController) {
    // Use LazyColumn to create a scrollable list
    LazyColumn {
        // Use items to display each setting as an item
        items(settings.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // without this indication parameter gets an error
                        indication = rememberRipple(
                            bounded = true
                        ),
                        onClick = {
                            navController.navigate(settings[index].route)
                        })
            ) {
                Text(getScreenTitle(settings[index]), modifier = Modifier.padding(16.dp))
            }
            Divider()
        }
    }
}

@Composable
private fun getScreenTitle(settingsRoute: SettingsRoutes): String {
    return when (settingsRoute) {
        SettingsRoutes.About -> stringResource(id = R.string.about)
        SettingsRoutes.PrivacyPolicy -> stringResource(id = R.string.privacy_policy)
        SettingsRoutes.TermsOfUse -> stringResource(id = R.string.terms_of_use)
    }
}
