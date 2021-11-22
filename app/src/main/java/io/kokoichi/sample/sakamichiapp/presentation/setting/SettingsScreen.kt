package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

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
                navigationList = navigation,
                viewModel = viewModel,
            )
        }
        composable(SettingScreen.UpdateBlogScreen.route) {
            UpdateBlogScreen(
                navController = navHostController,
                viewModel = viewModel,
            )
        }
        composable(SettingScreen.QuizResultsScreen.route) {
            QuizResultsScreen(
                uiState = uiState
            )
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
