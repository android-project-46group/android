package io.kokoichi.sample.sakamichiapp.presentation.member_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kokoichi.sample.sakamichiapp.presentation.util.components.CustomDevider

/**
 * Group selection bar
 */
@Composable
fun GroupBar(
    uiState: MemberListUiState,
    viewModel: MemberListViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        for (groupName: GroupName in GroupName.values()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        viewModel.setGroupName(groupName)
                        viewModel.setApiMembers()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Change text color if the group is selected.
                Text(
                    text = groupName.jname + "46",
                    color = if (groupName == uiState.groupName) {
                        MaterialTheme.colors.primary
                    } else {
                        Color.Gray
                    },
                    fontSize = 20.sp,
                )
                // Draw a double line if the group is selected.
                if (groupName == uiState.groupName) {
                    CustomDevider(MaterialTheme.colors.secondary, 1.dp)
                    CustomDevider(MaterialTheme.colors.background, 1.dp)
                    CustomDevider(MaterialTheme.colors.secondary, 1.dp)
                }
            }
        }
    }
}