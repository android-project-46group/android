package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kokoichi.sample.sakamichiapp.presentation.setting.pages.*
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.CustomSakaTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onThemeChanged: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    viewModel.readThemeFromDataStore(context)

    CustomSakaTheme {
        SettingsRouting(
            viewModel = viewModel,
            uiState = uiState,
            onThemeChanged = onThemeChanged,
        )
    }
}

@Composable
fun SettingsRouting(
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
    onThemeChanged: (String) -> Unit = {},
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
            SettingNavigation.SetTheme,
            SettingNavigation.ShareApp,
        )
        composable(SettingScreen.SettingTopScreen.route) {
            SettingTopScreen(
                navController = navHostController,
                navigationList = navigation,
                viewModel = viewModel,
                uiState = uiState,
            )
        }
        composable(SettingScreen.UpdateBlogScreen.route) {
            UpdateBlogScreen(
                navController = navHostController,
                viewModel = viewModel,
                uiState = uiState,
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
                uiState = uiState,
            )
        }
        composable(SettingScreen.ReportIssueScreen.route) {
            ReportIssueScreen(
                viewModel = viewModel,
                uiState = uiState,
            )
        }
        composable(SettingScreen.SetThemeScreen.route) {
            SetThemeScreen(
                navController = navHostController,
                viewModel = viewModel,
                selected = uiState.themeType.name,
                onThemeChanged = onThemeChanged,
            )
        }
        composable(SettingScreen.ShareAppScreen.route) {
            ShareAppScreen()
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

    object SetTheme : SettingNavigation(
        name = "テーマカラー設定",
        route = SettingScreen.SetThemeScreen.route
    )

    object ShareApp : SettingNavigation(
        name = "このアプリをシェアする",
        route = SettingScreen.ShareAppScreen.route
    )
}
