package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member

/**
 * States for Member List UI.
 */
data class MemberListUiState(
    var favorites: Set<String> = emptySet(),
    var loaded: Boolean = false,
    var groupName: GroupName = GroupName.NOGIZAKA,
    var visibleStyle: VisibleMemberStyle = VisibleMemberStyle.DEFAULT,
    var sortKey: MemberListSortKeys = MemberListSortKeys.NONE,
    var sortType: SortOrderType = SortOrderType.ASCENDING,
    var narrowType: NarrowKeys = NarrowKeys.NONE,
    var visibleMembers: MutableList<Member> = mutableListOf(),
    var formationTitle: String = "",
)

enum class GroupName(val jname: String) {
    NOGIZAKA("乃木坂"),
    SAKURAZAKA("櫻坂"),
    HINATAZAKA("日向坂"),
}

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
enum class MemberListSortKeys(val jname: String){
    NONE("なし"),     // No sort (default).
    NAME("名前"),
    GENERATION("期別"),
    BLOOD_TYPE("血液型"),
    BIRTHDAY("生年月日"),
    MONTH_DAY("月日"),
    HEIGHT("身長"),
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
enum class NarrowKeys(val jname: String) {
    NONE("なし"),
    FIRST_GEN("1期生"),
    SECOND_GEN("2期生"),
    THIRD_GEN("3期生"),
    FORTH_GEN("4期生"),
}