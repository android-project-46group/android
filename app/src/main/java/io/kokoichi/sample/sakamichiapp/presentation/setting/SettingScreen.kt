package io.kokoichi.sample.sakamichiapp.presentation.setting

/**
 * Navigation-routing path.
 */
sealed class SettingScreen(val route: String) {
    object SettingTopScreen : SettingScreen("setting_top")
    object UpdateBlogScreen : SettingScreen("update_blog")
    object QuizResultsScreen : SettingScreen("quiz_results")
    object ClearCacheScreen : SettingScreen("clear_cache")
}
