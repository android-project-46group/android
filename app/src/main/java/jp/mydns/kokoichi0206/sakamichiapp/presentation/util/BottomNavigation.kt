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
import jp.mydns.kokoichi0206.blog.navigation.blogRoute
import jp.mydns.kokoichi0206.blog.navigation.blogScreen
import jp.mydns.kokoichi0206.common.components.WebViewWidget
import jp.mydns.kokoichi0206.common.components.webViewProps
import jp.mydns.kokoichi0206.common.components.webViewRoute
import jp.mydns.kokoichi0206.member_detail.navigation.memberDetailRoute
import jp.mydns.kokoichi0206.member_detail.navigation.memberDetailScreen
import jp.mydns.kokoichi0206.member_detail.navigation.memberJson
import jp.mydns.kokoichi0206.member_list.navigation.memberListRoute
import jp.mydns.kokoichi0206.member_list.navigation.memberListScreen
import jp.mydns.kokoichi0206.model.getBlogUrlProps
import jp.mydns.kokoichi0206.model.getJsonFromMember
import jp.mydns.kokoichi0206.positions.navigation.positionsRoute
import jp.mydns.kokoichi0206.positions.navigation.positionsScreen
import jp.mydns.kokoichi0206.quiz.navigation.quizRoute
import jp.mydns.kokoichi0206.quiz.navigation.quizScreen
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.settings.getBaseColorInThemeTypesFromString
import jp.mydns.kokoichi0206.settings.getSubColorInThemeTypesFromString
import jp.mydns.kokoichi0206.settings.navigation.settingsRoute
import jp.mydns.kokoichi0206.settings.navigation.settingsScreen

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
        memberListScreen {
            navHostController.navigateUp()
            navHostController.navigate(
                memberDetailRoute
                        + "/$memberJson=${getJsonFromMember(it)}"
            )
        }

        memberDetailScreen()

        blogScreen {
            val navPath = webViewRoute +
                    "/$webViewProps=${getBlogUrlProps(it)}"
            navHostController.navigate(navPath)
        }

        positionsScreen()

        quizScreen()

        settingsScreen(onThemeChanged)

        /**
         * WebView composable (util)
         */
        composable(
            webViewRoute
                    + "/$webViewProps={$webViewProps}",
            arguments = listOf(
                navArgument(webViewProps) {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            var url = backStackEntry.arguments?.getString(webViewProps)
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
) {
    object Home : BottomNavItem(
        name = R.string.bottom_nav_members,
        route = memberListRoute,
        icons = R.drawable.members,
    )

    object Blog : BottomNavItem(
        name = R.string.bottom_nav_blog,
        route = blogRoute,
        icons = R.drawable.blog,
    )

    object Position : BottomNavItem(
        name = R.string.bottom_nav_position,
        route = positionsRoute,
        icons = R.drawable.positions,
    )

    object Quiz : BottomNavItem(
        name = R.string.bottom_nav_quiz,
        route = quizRoute,
        icons = R.drawable.quiz,
    )

    object Setting : BottomNavItem(
        name = R.string.bottom_nav_settings,
        route = settingsRoute,
        icons = R.drawable.settings,
    )
}
