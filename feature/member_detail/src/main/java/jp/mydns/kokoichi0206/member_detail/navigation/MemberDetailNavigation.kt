package jp.mydns.kokoichi0206.member_detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import jp.mydns.kokoichi0206.member_detail.MemberDetailScreen
import jp.mydns.kokoichi0206.model.Member


const val memberDetailRoute = "member_detail_route"
const val memberJson = "memberJson"

fun NavGraphBuilder.memberDetailScreen() {
    composable(
        route = memberDetailRoute
                + "/$memberJson={$memberJson}",
        arguments = listOf(
            navArgument(
                memberJson
            ) { type = NavType.StringType }
        ),
    ) { backStackEntry ->

        val memberJson = backStackEntry.arguments?.getString(memberJson)

        // Parse Json to Member class object
        val member = Gson().fromJson(
            memberJson,
            Member::class.java
        )

        MemberDetailScreen(member)
    }
}
