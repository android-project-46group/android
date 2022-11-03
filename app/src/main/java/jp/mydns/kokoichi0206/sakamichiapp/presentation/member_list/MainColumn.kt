package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components.OnePerson
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Screen
import jp.mydns.kokoichi0206.common.getGenerationLooper
import jp.mydns.kokoichi0206.model.getJsonFromMember

/**
 * Main column to display members list.
 */
@Composable
fun MainColumn(
    uiState: MemberListUiState,
    navController: NavController
) {
    when (uiState.visibleStyle) {
        VisibleMemberStyle.DEFAULT, VisibleMemberStyle.BIRTHDAY ->
            DefaultColumn(
                uiState = uiState,
                navController = navController
            )
        VisibleMemberStyle.LINES ->
            ColumnWithLine(
                uiState = uiState,
                navController = navController
            )
    }
}

@Composable
fun DefaultColumn(
    uiState: MemberListUiState,
    navController: NavController,
    members: MutableList<jp.mydns.kokoichi0206.model.Member> = uiState.visibleMembers,
) {
    LazyColumn(
        contentPadding = Constants.BottomBarPadding,
    ) {
        val itemCount = if (members.size % 3 == 0) {
            members.size / 3
        } else {
            members.size / 3 + 1
        }
        items(itemCount) {
            MemberRow(
                rowIndex = it,
                entries = members,
                navController = navController,
                uiState = uiState,
            )
        }
    }
}

@Composable
fun ColumnWithLine(
    uiState: MemberListUiState,
    navController: NavController
) {
    val sameGroupMembers = when (uiState.sortKey) {
        MemberListSortKeys.BLOOD_TYPE ->
            uiState.visibleMembers.groupBy {
                when (it.bloodType) {
                    "A型" -> "A型"
                    "B型" -> "B型"
                    "O型" -> "O型"
                    "AB型" -> "AB型"
                    else -> "不明"
                }
            }
        else ->
            uiState.visibleMembers.groupBy {
                when (it.generation) {
                    "1期生" -> "1期生"
                    "2期生" -> "2期生"
                    "3期生" -> "3期生"
                    "4期生" -> "4期生"
                    else -> "不明"
                }
            }
    }
    val looperStr = when (uiState.sortKey) {
        MemberListSortKeys.BLOOD_TYPE ->
            Constants.BLOOD_TYPE_LIST
        else ->
            getGenerationLooper(uiState.groupName.jname)
    }
    LazyColumn(
        contentPadding = Constants.BottomBarPadding,
    ) {
        for (type in looperStr) {
            item {
                Text(
                    text = type,
                    modifier = Modifier
                        .padding(
                            start = SpaceMedium,
                            bottom = SpaceSmall,
                        )
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary,
                )
            }

            val members = if (sameGroupMembers[type] == null) {
                mutableListOf<jp.mydns.kokoichi0206.model.Member>()
            } else {
                sameGroupMembers[type]!!.toMutableList()
            }

            val itemCount = if (members.size % 3 == 0) {
                members.size / 3
            } else {
                members.size / 3 + 1
            }

            items(itemCount) {
                MemberRow(
                    rowIndex = it,
                    entries = members,
                    navController = navController,
                    uiState = uiState,
                )
            }

            item {
                Spacer(modifier = Modifier.height(SpaceSmall))
            }
        }
    }
}

/**
 * One row of mainColumn.
 */
@Composable
fun MemberRow(
    rowIndex: Int,
    entries: MutableList<jp.mydns.kokoichi0206.model.Member>,
    navController: NavController,
    uiState: MemberListUiState,
) {
    // Three members in one row
    Row {
        WrapOnePerson(
            member = entries[rowIndex * 3],
            modifier = Modifier.weight(1f),
            navController = navController,
            uiState = uiState,
        )
        Spacer(modifier = Modifier.width(SpaceMedium))
        if (entries.size >= rowIndex * 3 + 2) {
            WrapOnePerson(
                member = entries[rowIndex * 3 + 1],
                modifier = Modifier.weight(1f),
                navController = navController,
                uiState = uiState,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        if (entries.size >= rowIndex * 3 + 3) {
            WrapOnePerson(
                member = entries[rowIndex * 3 + 2],
                modifier = Modifier.weight(1f),
                navController = navController,
                uiState = uiState,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    Spacer(modifier = Modifier.height(SpaceMedium))
}

@Composable
fun WrapOnePerson(
    member: jp.mydns.kokoichi0206.model.Member,
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: MemberListUiState,
) {
    // Change display information according to selected sortKey.
    val extraInfo = when (uiState.sortKey) {
        MemberListSortKeys.BIRTHDAY, MemberListSortKeys.MONTH_DAY ->
            member.birthday
        MemberListSortKeys.HEIGHT ->
            member.height
        else ->
            null
    }
    OnePerson(
        member = member,
        modifier = modifier,
        onclick = {
            navController.navigateUp()
            navController.navigate(
                Screen.MemberDetailScreen.route
                        + "/${Constants.NAV_PARAM_MEMBER_PROPS}=${getJsonFromMember(member)}"
            )
        },
        extraInfo = extraInfo,
    )
}
