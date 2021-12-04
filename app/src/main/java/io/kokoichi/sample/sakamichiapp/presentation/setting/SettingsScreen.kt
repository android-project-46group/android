package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.kokoichi.sample.sakamichiapp.presentation.setting.pages.*
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.CustomSakaTheme
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants

@ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@Composable
fun SettingsRouting(
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
    onThemeChanged: (String) -> Unit = {},
) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = SettingScreen.SettingTopScreen.route,
        enterTransition = { _, _ ->
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
            ) + fadeIn(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
        },
        popExitTransition = { _, _ ->
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
            ) + fadeOut(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
        },
    ) {
        val navigation = listOf(
            SettingNavigation.UpdateBlog,
            SettingNavigation.QuizResult,
            SettingNavigation.ClearCache,
            SettingNavigation.ReportIssue,
            SettingNavigation.SetTheme,
            SettingNavigation.ShareApp,
        )

        composable(
            route = SettingScreen.SettingTopScreen.route,
            exitTransition = { _, _ ->
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
                ) + fadeOut(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
            },
            popEnterTransition = { _, _ ->
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
                ) + fadeIn(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
            }
        ) {
            SettingTopScreen(
                navController = navController,
                navigationList = navigation,
                viewModel = viewModel,
                uiState = uiState,
            )
        }
        composable(SettingScreen.UpdateBlogScreen.route) {
            UpdateBlogScreen(
                navController = navController,
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
                navController = navController,
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
                navController = navController,
                viewModel = viewModel,
                selected = uiState.themeType.name,
                onThemeChanged = onThemeChanged,
            )
        }
        composable(SettingScreen.ShareAppScreen.route) {
            ShareAppScreen(
                navController = navController,
                uiState = uiState,
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

    object SetTheme : SettingNavigation(
        name = "テーマカラー設定",
        route = SettingScreen.SetThemeScreen.route
    )

    object ShareApp : SettingNavigation(
        name = "このアプリをシェアする",
        route = SettingScreen.ShareAppScreen.route
    )
}
