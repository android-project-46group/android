package jp.mydns.kokoichi0206.blog

import jp.mydns.kokoichi0206.common.GroupName

/**
 * States for Member List UI.
 */
data class BlogUiState(
    var loaded: Boolean = false,
    var isLoading: Boolean = false,
    var error: String = "",
    var groupName: GroupName = GroupName.NOGIZAKA,
    var blogs: List<jp.mydns.kokoichi0206.model.Blog> = emptyList(),
    // Sort blogs by lastUpdatedTime or memberName.
    // If this flag is true, it means by lastUpdatedTime.
    var isSortTime: Boolean = false,
    var isRefreshing: Boolean = false,
)
