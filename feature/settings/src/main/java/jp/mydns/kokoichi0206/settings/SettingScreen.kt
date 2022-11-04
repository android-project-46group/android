package jp.mydns.kokoichi0206.settings

/**
 * Navigation-routing path.
 */
sealed class SettingScreen(val route: String) {
    object SettingTopScreen : SettingScreen("setting_top")
    object UpdateBlogScreen : SettingScreen("update_blog")
    object QuizResultsScreen : SettingScreen("quiz_results")
    object ClearCacheScreen : SettingScreen("clear_cache")
    object ReportIssueScreen : SettingScreen("report_issue")
    object SetThemeScreen : SettingScreen("set_theme")
    object ShareAppScreen : SettingScreen("share_app")
    object AboutAppScreen : SettingScreen("about_app")
}