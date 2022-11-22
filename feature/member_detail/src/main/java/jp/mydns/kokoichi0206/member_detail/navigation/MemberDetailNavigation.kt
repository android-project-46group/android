package jp.mydns.kokoichi0206.member_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.squareup.moshi.Moshi
import jp.mydns.kokoichi0206.member_detail.MemberDetailScreen
import jp.mydns.kokoichi0206.model.Member
import jp.mydns.kokoichi0206.model.getJsonFromMember


const val memberDetailRoute = "member_detail_route"
const val memberJson = "memberJson"

fun NavController.navigateToMemberDetail(member: Member) {
    this.navigateUp()
    this.navigate(
        memberDetailRoute
                + "/$memberJson=${getJsonFromMember(member)}"
    )
}

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
        memberJson?.let {
            // Parse Json to Member class object
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(Member::class.java)

            val member = jsonAdapter.fromJson(memberJson)

            member?.let {
                MemberDetailScreen(member)
            }
        }
    }
}
