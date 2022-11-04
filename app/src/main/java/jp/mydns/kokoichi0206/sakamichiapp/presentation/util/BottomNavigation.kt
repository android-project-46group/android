package jp.mydns.kokoichi0206.sakamichiapp.presentation.util

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.blog.BlogScreenWithCustomTheme
import jp.mydns.kokoichi0206.member_detail.MemberDetailScreen
import jp.mydns.kokoichi0206.positions.PositionsScreen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz.QuizScreen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsScreen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.getBaseColorInThemeTypesFromString
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.getSubColorInThemeTypesFromString
import jp.mydns.kokoichi0206.common.components.WebViewWidget
import jp.mydns.kokoichi0206.member_list.MemberListScreen
import jp.mydns.kokoichi0206.model.getJsonFromMember

/**
 * Bottom navigation host setup (Register routing).
 */
@ExperimentalAnimationApi
@Composable
fun BottomNavHost(
    navHostController: NavHostController,
    onThemeChanged: (String) -> Unit
) {
    // Change color of ActionBar using systemuicontroller.
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.White,
    )
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Home.route
    ) {
        /**
         * Member List Screen
         */
        composable(Screen.MemberListScreen.route) {
            MemberListScreen { member ->
                navHostController.navigateUp()
                navHostController.navigate(
                    Screen.MemberDetailScreen.route
                            + "/${Constants.NAV_PARAM_MEMBER_PROPS}=${getJsonFromMember(member)}"
                )
            }
        }

        /**
         * Member Detail Screen
         */
        composable(
            Screen.MemberDetailScreen.route
                    + "/${Constants.NAV_PARAM_MEMBER_PROPS}={${Constants.NAV_PARAM_MEMBER_PROPS}}",
            arguments = listOf(
                navArgument(
                    Constants.NAV_PARAM_MEMBER_PROPS
                ) { type = NavType.StringType })
        ) { backStackEntry ->

            val memberJson = backStackEntry.arguments?.getString(Constants.NAV_PARAM_MEMBER_PROPS)

            // Parse Json to Member class object
            val member = Gson().fromJson<jp.mydns.kokoichi0206.model.Member>(
                memberJson,
                jp.mydns.kokoichi0206.model.Member::class.java
            )

            MemberDetailScreen(member)
        }

        /**
         * Blog Screen
         */
        composable(
            Screen.BlogScreen.route
        ) {
            BlogScreenWithCustomTheme(
                navController = navHostController,
            )
        }

        /**
         * Position Screen
         */
        composable(
            Screen.PositionScreen.route
        ) {
            PositionsScreen()
        }

        /**
         * Quiz Screen
         */
        composable(
            Screen.QuizScreen.route
        ) {
            QuizScreen()
        }

        /**
         * Settings Screen
         */
        composable(
            Screen.SettingScreen.route
        ) {
            SettingsScreen(
                onThemeChanged = onThemeChanged,
            )
        }

        /**
         * WebView composable (util)
         */
        composable(
            Screen.WebViewScreen.route
                    + "/${Constants.NAV_PARAM_WEBVIEW_PROPS}={${Constants.NAV_PARAM_WEBVIEW_PROPS}}",
            arguments = listOf(
                navArgument(Constants.NAV_PARAM_WEBVIEW_PROPS) {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            var url = backStackEntry.arguments?.getString(Constants.NAV_PARAM_WEBVIEW_PROPS)
            if (url == null) {
                // Default URL
                url = "https://blog.nogizaka46.com/"
            }
            WebViewWidget(contentUrl = url)
        }
    }
}

/**
 * Bottom bar composable function.
 */
@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    themeType: String = "",
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    // Colors used for bottom bar
    val selectedColor = getBaseColorInThemeTypesFromString(themeType)
    val unSelectedColor = getSubColorInThemeTypesFromString(themeType).copy(alpha = 0.4f)

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 5.dp,
    ) {
        items.forEach { item ->
            val selected = item.route == currentRoute
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    // Always only one route is stocked
                    navController.popBackStack()
                    navController.navigate(item.route)
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = unSelectedColor,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.icons),
                            contentDescription = "${item.name}"
                        )
                        Text(
                            text = stringResource(item.name),
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            color = if (selected) {
                                selectedColor
                            } else {
                                unSelectedColor
                            },
                        )
                    }
                },
            )
        }
    }
}

/**
 * Information of bottom bar icons.
 */
sealed class BottomNavItem(
    val name: Int,
    val route: String,
    @DrawableRes val icons: Int,
    val badgeCount: Int = 0,
) {
    object Home : BottomNavItem(
        name = R.string.bottom_nav_members,
        route = Screen.MemberListScreen.route,
        icons = R.drawable.members,
    )

    object Blog : BottomNavItem(
        name = R.string.bottom_nav_blog,
        route = Screen.BlogScreen.route,
        icons = R.drawable.blog,
    )

    object Position : BottomNavItem(
        name = R.string.bottom_nav_position,
        route = Screen.PositionScreen.route,
        icons = R.drawable.positions,
    )

    object Quiz : BottomNavItem(
        name = R.string.bottom_nav_quiz,
        route = Screen.QuizScreen.route,
        icons = R.drawable.quiz,
    )

    object Setting : BottomNavItem(
        name = R.string.bottom_nav_settings,
        route = Screen.SettingScreen.route,
        icons = R.drawable.settings,
    )
}
