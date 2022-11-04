package jp.mydns.kokoichi0206.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.settings.pages.*

@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onThemeChanged: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.readVersion()
        viewModel.readUserID(context)
        viewModel.readThemeFromDataStore(context)
    }

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
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
            ) + fadeIn(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
        },
        popExitTransition = {
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
            SettingNavigation.AboutApp,
        )

        composable(
            route = SettingScreen.SettingTopScreen.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS)
                ) + fadeOut(animationSpec = tween(Constants.NAVIGATION_DURATION_MILLIS))
            },
            popEnterTransition = {
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
        composable(SettingScreen.AboutAppScreen.route) {
            AboutAppScreen(
                navController = navController,
                uiState = uiState,
            )
        }
    }
}

sealed class SettingNavigation(
    val name: Int,
    val route: String,
    val badgeCount: Int = 0,
) {
    /**
     * UpdateBlogScreen
     */
    object UpdateBlog : SettingNavigation(
        name = R.string.setting_update_blog,
        route = SettingScreen.UpdateBlogScreen.route,
    )

    object QuizResult : SettingNavigation(
        name = R.string.setting_quiz_record,
        route = SettingScreen.QuizResultsScreen.route,
    )

    object ClearCache : SettingNavigation(
        name = R.string.setting_clear_cache,
        route = SettingScreen.ClearCacheScreen.route,
    )

    object ReportIssue : SettingNavigation(
        name = R.string.setting_report_bugs,
        route = SettingScreen.ReportIssueScreen.route
    )

    object SetTheme : SettingNavigation(
        name = R.string.setting_set_theme_color,
        route = SettingScreen.SetThemeScreen.route
    )

    object ShareApp : SettingNavigation(
        name = R.string.setting_share_app,
        route = SettingScreen.ShareAppScreen.route
    )

    object AboutApp : SettingNavigation(
        name = R.string.setting_about_app,
        route = SettingScreen.AboutAppScreen.route
    )
}
