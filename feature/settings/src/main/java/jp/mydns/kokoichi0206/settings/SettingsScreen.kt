package jp.mydns.kokoichi0206.settings

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.model.Member
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
        viewModel.readAppName()
        viewModel.readUserID(context)
        viewModel.readThemeFromDataStore(context)
        viewModel.initAllMembers()
        viewModel.readFavesFromDataStore(context)
    }

    CustomSakaTheme {
        SettingsRouting(
            uiState = uiState,
            onThemeChanged = onThemeChanged,
            onIsDevChanged = {
                viewModel.writeIsDevTrue(context)
            },
            onUpdateClicked = {
                viewModel.updateBlog()
            },
            reportIssue = {
                viewModel.reportIssue(it)
            },
            setThemeType = { type ->
                viewModel.setThemeType(type)
                viewModel.writeTheme(context, type.name)
            },
            onFaveNavigated = {
                viewModel.readFavesFromDataStore(context)
            },
            onImageSelected = {
                viewModel.writeFaveUri(context, it.toString())
            },
            onConfirmClicked = {
                viewModel.selected(context, it)
            },
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun SettingsRouting(
    uiState: SettingsUiState,
    onThemeChanged: (String) -> Unit = {},
    onIsDevChanged: () -> Unit = {},
    onUpdateClicked: () -> Unit = {},
    reportIssue: (String) -> Unit = {},
    setThemeType: (ThemeType) -> Unit = {},
    onFaveNavigated: () -> Unit = {},
    onImageSelected: (Uri) -> Unit = {},
    onConfirmClicked: (Member) -> Unit = {},
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
            SettingNavigation.MyFave,
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
                uiState = uiState,
                onIsDevChanged = onIsDevChanged,
            )
        }
        composable(SettingScreen.UpdateBlogScreen.route) {
            UpdateBlogScreen(
                navController = navController,
                uiState = uiState,
                onUpdateClicked = onUpdateClicked,
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
                uiState = uiState,
                reportIssue = reportIssue,
            )
        }
        composable(SettingScreen.SetThemeScreen.route) {
            SetThemeScreen(
                navController = navController,
                selected = uiState.themeType.name,
                onThemeChanged = onThemeChanged,
                setThemeType = setThemeType,
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

        composable(SettingScreen.MyFaveScreen.route) {
            FaveScreen(
                navController = navController,
                onFaveNavigated = onFaveNavigated,
                onImageSelected = {
                    onImageSelected(it)
                },
                uiState = uiState,
            )
        }
        var selected = uiState.fave
        composable(SettingScreen.MyFaveSettingScreen.route) {
            FaveSettingScreen(
                navController = navController,
                uiState = uiState,
                onConfirmClicked = {
                    selected = it
                    navController.navigate(SettingScreen.MyFaveSettingConfirmScreen.route)
                }
            )
        }
        composable(SettingScreen.MyFaveSettingConfirmScreen.route) {
            selected?.let {
                FaveSettingConfirmScreen(
                    uiState = uiState,
                    selected = it,
                    onConfirmClicked = {
                        onConfirmClicked(it)
                        navController.navigate(SettingScreen.MyFaveScreen.route)
                    }
                )
            }
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

    object MyFave : SettingNavigation(
        name = R.string.my_fave,
        route = SettingScreen.MyFaveScreen.route
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun SettingsScreenPreview() {
    val uiState = SettingsUiState(
        version = "1.0.9",
    )
    Box(modifier = Modifier.background(Color.White)) {
        SettingsRouting(uiState = uiState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun SettingsScreenWithThemePreview() {
    val uiState = SettingsUiState(
        version = "1.0.9",
        themeType = ThemeType.Sakurazaka,
    )
    Box(modifier = Modifier.background(Color.White)) {
        SettingsRouting(uiState = uiState)
    }
}
