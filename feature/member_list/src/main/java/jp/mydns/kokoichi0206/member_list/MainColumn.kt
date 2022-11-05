package jp.mydns.kokoichi0206.member_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.member_list.components.OnePerson
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.common.getGenerationLooper
import jp.mydns.kokoichi0206.model.Member

/**
 * Main column to display members list.
 */
@Composable
fun MainColumn(
    uiState: MemberListUiState,
    onPersonClick: (Member) -> Unit,
) {
    when (uiState.visibleStyle) {
        VisibleMemberStyle.DEFAULT, VisibleMemberStyle.BIRTHDAY ->
            DefaultColumn(
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        VisibleMemberStyle.LINES ->
            ColumnWithLine(
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
    }
}

@Composable
fun DefaultColumn(
    uiState: MemberListUiState,
    members: MutableList<jp.mydns.kokoichi0206.model.Member> = uiState.visibleMembers,
    onPersonClick: (Member) -> Unit = {},
) {
    LazyColumn {
        val itemCount = if (members.size % 3 == 0) {
            members.size / 3
        } else {
            members.size / 3 + 1
        }
        items(itemCount) {
            MemberRow(
                rowIndex = it,
                entries = members,
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        }
    }
}

@Composable
fun ColumnWithLine(
    uiState: MemberListUiState,
    onPersonClick: (Member) -> Unit = {},
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
    LazyColumn {
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
                    uiState = uiState,
                    onPersonClick = onPersonClick,
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
    entries: MutableList<Member>,
    uiState: MemberListUiState,
    onPersonClick: (Member) -> Unit,
) {
    // Three members in one row
    Row {
        WrapOnePerson(
            member = entries[rowIndex * 3],
            modifier = Modifier.weight(1f),
            uiState = uiState,
            onPersonClick = onPersonClick,
        )
        Spacer(modifier = Modifier.width(SpaceMedium))
        if (entries.size >= rowIndex * 3 + 2) {
            WrapOnePerson(
                member = entries[rowIndex * 3 + 1],
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        if (entries.size >= rowIndex * 3 + 3) {
            WrapOnePerson(
                member = entries[rowIndex * 3 + 2],
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    Spacer(modifier = Modifier.height(SpaceMedium))
}

@Composable
fun WrapOnePerson(
    member: Member,
    modifier: Modifier = Modifier,
    uiState: MemberListUiState,
    onPersonClick: (Member) -> Unit,
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
            onPersonClick(member)
        },
        extraInfo = extraInfo,
    )
}
