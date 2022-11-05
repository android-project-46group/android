package jp.mydns.kokoichi0206.settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.mydns.kokoichi0206.settings.SettingsScreen


const val settingsRoute = "settings_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsScreen(
    onThemeChanged: (String) -> Unit,
) {
    composable(route = settingsRoute) {
        SettingsScreen {
            onThemeChanged(it)
        }
    }
}
