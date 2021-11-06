package io.kokoichi.sample.sakamichiapp.presentation.util

/**
 * Navigation-routing path.
 */
sealed class Screen(val route: String) {
    object MemberListScreen : Screen("member_list_screen")
    object MemberDetailScreen : Screen("member_detail_screen")
    object PositionScreen : Screen("position_screen")
    object BlogScreen : Screen("blog_screen")
}
