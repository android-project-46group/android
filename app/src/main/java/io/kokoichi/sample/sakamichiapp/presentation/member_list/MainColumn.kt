package io.kokoichi.sample.sakamichiapp.presentation.member_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.presentation.member_list.components.OnePerson
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants
import io.kokoichi.sample.sakamichiapp.presentation.util.Screen
import io.kokoichi.sample.sakamichiapp.presentation.util.getGenerationLooper
import io.kokoichi.sample.sakamichiapp.presentation.util.getJsonFromMember

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
    members: MutableList<Member> = uiState.visibleMembers,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(top = 10.dp, bottom = 56.dp, start = 20.dp, end = 20.dp),
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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(top = 10.dp, bottom = 56.dp, start = 20.dp, end = 20.dp),
    ) {
        for (type in looperStr) {
            item {
                Text(
                    text = type,
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(start = 10.dp)
                        .fillMaxWidth(),
                    fontSize = 36.sp,
                    color = MaterialTheme.colors.primary,
                )
            }

            val members = if (sameGroupMembers[type] == null) {
                mutableListOf<Member>()
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
                Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.width(16.dp))
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
        Spacer(modifier = Modifier.width(16.dp))
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
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun WrapOnePerson(
    member: Member,
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: MemberListUiState,
) {
    // Change display information according to selected sortKey.
    when (uiState.sortKey) {
        MemberListSortKeys.BIRTHDAY, MemberListSortKeys.MONTH_DAY ->
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
                extraInfo = member.birthday,
            )
        MemberListSortKeys.HEIGHT ->
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
                extraInfo = member.height,
            )
        else ->
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
            )
    }
}
