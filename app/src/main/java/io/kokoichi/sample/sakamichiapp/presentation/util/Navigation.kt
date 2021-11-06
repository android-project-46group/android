package io.kokoichi.sample.sakamichiapp.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.presentation.member_detail.MemberDetailScreen
import io.kokoichi.sample.sakamichiapp.presentation.member_list.MemberListScreen

/**
 * Compose navigation set-up.
 */
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MemberListScreen.route
    ) {
        /**
         * Member List Screen
         */
        composable(Screen.MemberListScreen.route) {
            MemberListScreen(
                navController = navController,
            )
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
            val member = Gson().fromJson<Member>(memberJson, Member::class.java)

            MemberDetailScreen(member)
        }
    }
}