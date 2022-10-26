package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Blog
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.GroupName

/**
 * States for Member List UI.
 */
data class BlogUiState(
    var loaded: Boolean = false,
    var isLoading: Boolean = false,
    var error: String = "",
    var groupName: GroupName = GroupName.NOGIZAKA,
    var blogs: List<Blog> = emptyList(),
    // Sort blogs by lastUpdatedTime or memberName.
    // If this flag is true, it means by lastUpdatedTime.
    var isSortTime: Boolean = false,
    var isRefreshing: Boolean = false,
)
