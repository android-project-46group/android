package jp.mydns.kokoichi0206.member_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.mydns.kokoichi0206.member_list.MemberListScreen
import jp.mydns.kokoichi0206.model.Member

const val memberListRoute = "member_list_route"

fun NavGraphBuilder.memberListScreen(
    onMemberClick: (Member) -> Unit,
) {
    composable(route = memberListRoute) {
        MemberListScreen {
            onMemberClick(it)
        }
    }
}
