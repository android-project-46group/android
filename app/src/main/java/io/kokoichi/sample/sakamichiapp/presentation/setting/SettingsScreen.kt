package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.CustomSakaTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // TODO: カスタムテーマカラーを作るならここ？
    CustomSakaTheme {
        SettingsRouting(
            viewModel = viewModel,
            uiState = uiState,
        )
    }
}

@Composable
fun SettingsRouting(
    viewModel: SettingsViewModel,
    uiState: SettingsUiState
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = SettingScreen.SettingTopScreen.route,
    ) {
        val navigation = listOf(
            SettingNavigation.UpdateBlog,
            SettingNavigation.QuizResult,
            SettingNavigation.ClearCache,
            SettingNavigation.ReportIssue,
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
        composable(SettingScreen.ReportIssueScreen.route) {
            ReportIssueScreen(
                viewModel = viewModel,
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

    object ReportIssue : SettingNavigation(
        name = "不具合の報告／意見",
        route = SettingScreen.ReportIssueScreen.route
    )
}
