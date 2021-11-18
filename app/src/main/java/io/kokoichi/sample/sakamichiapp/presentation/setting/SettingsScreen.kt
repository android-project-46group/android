package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = SettingScreen.SettingTopScreen.route,
    ) {
        val navigation = listOf(
            SettingNavigation.UpdateBlog,
            SettingNavigation.QuizResult,
            SettingNavigation.ClearCache,
        )
        composable(SettingScreen.SettingTopScreen.route) {
            SettingTopScreen(
                navController = navHostController,
                navigationList = navigation
            )
        }
        composable(SettingScreen.UpdateBlogScreen.route) {
            UpdateBlogScreen()
        }
        composable(SettingScreen.QuizResultsScreen.route) {
            QuizResultsScreen()
        }
        composable(SettingScreen.ClearCacheScreen.route) {
            CacheClearDialog(
                navController = navHostController,
            )
        }
    }
}

sealed class SettingNavigation(
    val name: String,
    val route: String,
    val badgeCount: Int = 0,
) {
    /**
     * UpdateBlogScreen
     */
    object UpdateBlog : SettingNavigation(
        name = "Blog 情報を更新する",
        route = SettingScreen.UpdateBlogScreen.route,
    )

    object QuizResult : SettingNavigation(
        name = "クイズ成績",
        route = SettingScreen.QuizResultsScreen.route,
    )

    object ClearCache : SettingNavigation(
        name = "キャッシュをクリア",
        route = SettingScreen.ClearCacheScreen.route,
    )
}
