package io.kokoichi.sample.sakamichiapp.presentation.blog

import io.kokoichi.sample.sakamichiapp.domain.model.Blog
import io.kokoichi.sample.sakamichiapp.presentation.member_list.GroupName

/**
 * States for Member List UI.
 */
data class BlogUiState(
    var loaded: Boolean = false,
    var groupName: GroupName = GroupName.NOGIZAKA,
    var blogs: List<Blog> = emptyList(),
    // Sort blogs by lastUpdatedTime or memberName.
    // If this flag is true, it means by lastUpdatedTime.
    var isSortTime: Boolean = false,
)
