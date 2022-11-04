package jp.mydns.kokoichi0206.blog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.mydns.kokoichi0206.blog.BlogScreenWithCustomTheme
import jp.mydns.kokoichi0206.model.Blog


const val blogRoute = "blog_route"

fun NavGraphBuilder.blogScreen(
    onClick: (Blog) -> Unit,
) {
    composable(route = blogRoute) {
        BlogScreenWithCustomTheme {
            onClick(it)
        }
    }
}
