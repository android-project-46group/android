package jp.mydns.kokoichi0206.member_list

import android.content.Context
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.feature.member_list.R
import jp.mydns.kokoichi0206.model.Member

/**
 * States for Member List UI.
 */
data class MemberListUiState(
    var favorites: Set<String> = emptySet(),
    var loaded: Boolean = false,
    var isLoading: Boolean = false,
    var error: String = "",
    var groupName: GroupName = GroupName.NOGIZAKA,
    var visibleStyle: VisibleMemberStyle = VisibleMemberStyle.DEFAULT,
    var sortKey: MemberListSortKeys = MemberListSortKeys.NONE,
    var sortType: SortOrderType = SortOrderType.ASCENDING,
    var narrowType: NarrowKeys = NarrowKeys.NONE,
    var visibleMembers: MutableList<Member> = mutableListOf(),
    var formationTitle: String = "",
    var isRefreshing: Boolean = false,
)

/**
 * Main LazyColumn representing Style.
 * This is different according to sortKey.
 *
 * DEFAULT: NO order type (NONE)
 * BIRTHDAY: Display birthday below the image
 * LINES: Draw lines to separate discrete values (like blood_type)
 */
enum class VisibleMemberStyle {
    DEFAULT, BIRTHDAY, LINES
}

/**
 * Keys to sort members.
 */
enum class MemberListSortKeys(val nameId: Int) {
    NONE(R.string.sort_key_none),     // No sort (default).
    NAME(R.string.sort_key_name),
    GENERATION(R.string.sort_key_generation),
    BLOOD_TYPE(R.string.sort_key_blood_type),
    BIRTHDAY(R.string.sort_key_birthday),
    MONTH_DAY(R.string.sort_key_day),
    HEIGHT(R.string.sort_key_height);

    fun getStringResource(context: Context): String {
        return context.getString(nameId)
    }
}

/**
 * Sorting Order Types.
 */
enum class SortOrderType(val type: String) {
    ASCENDING("昇順"),
    DESCENDING("降順"),
}

/**
 * Keys to narrow down members.
 */
enum class NarrowKeys(val nameId: Int) {
    NONE(R.string.narrow_key_none),
    FIRST_GEN(R.string.narrow_key_1st),
    SECOND_GEN(R.string.narrow_key_2nd),
    THIRD_GEN(R.string.narrow_key_3rd),
    FORTH_GEN(R.string.narrow_key_4th);

    fun getStringResource(context: Context): String {
        return context.getString(nameId)
    }
}