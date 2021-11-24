package io.kokoichi.sample.sakamichiapp.presentation.member_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.member_list.*
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.SpaceHuge
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.SpaceSmall
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.SpaceTiny

/**
 * Bar to change displaying member information.
 */
@Composable
fun SortBar(
    uiState: MemberListUiState,
    viewModel: MemberListViewModel,
) {
    /**
     * Sort Part
     */
    Row(
        modifier = Modifier
            .padding(horizontal = SpaceSmall, vertical = SpaceTiny)
            .fillMaxWidth()
            .height(SpaceHuge)
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(SpaceTiny),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 0.dp),
                text = stringResource(R.string.member_list_sort_key),
                fontSize = 10.sp,
            )
        }

        var sortExpanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f),
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 0.dp),
                onClick = {
                    sortExpanded = true
                },
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 0.dp),
                    text = uiState.sortKey.jname,
                    fontSize = 8.sp,
                )
            }
        }
        DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
            for (sortKey: MemberListSortKeys in MemberListSortKeys.values()) {
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false
                        viewModel.setSortKey(sortKey)
                        viewModel.sortMembers()
                        // Change the visible(show) style
                        viewModel.setVisibleStyle(sortKey)
                    }
                ) {
                    Text(
                        text = sortKey.jname,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }

        // Change Sort Order Button (Ascending or Descending)
        BoxWithConstraints(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .weight(1f)
                .background(MaterialTheme.colors.secondary)
        ) {
            val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
            val boxHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }

            val length = min(boxWidth, boxHeight)

            val resId = when (uiState.sortType) {
                SortOrderType.ASCENDING ->
                    R.drawable.up
                SortOrderType.DESCENDING ->
                    R.drawable.down
            }
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier
                    .size(length)
                    .clip(CutCornerShape(5.dp))
                    .clickable {
                        viewModel.setSortType(
                            when (uiState.sortType) {
                                SortOrderType.ASCENDING ->
                                    SortOrderType.DESCENDING
                                SortOrderType.DESCENDING ->
                                    SortOrderType.ASCENDING
                            }
                        )
                        // Notify viewModel to re-sort
                        viewModel.sortMembers()
                    },
                contentScale = ContentScale.Crop
            )
        }
        // Spacer
        Spacer(modifier = Modifier.weight(1f))


        /**
         * Narrow part
         */
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(SpaceTiny),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.member_list_narrow_down_key),
                fontSize = 10.sp
            )
        }

        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f)
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 0.dp),
                onClick = {
                    expanded = true
                }
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 0.dp),
                    text = uiState.narrowType.jname,
                    fontSize = 8.sp
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                val maxIndex = when (uiState.groupName) {
                    GroupName.SAKURAZAKA ->
                        2
                    GroupName.HINATAZAKA ->
                        3
                    else ->
                        4
                }

                NarrowKeys.values().forEachIndexed { index, nKey ->
                    if (index <= maxIndex) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false

                                // narrow down the visible members
                                viewModel.setNarrowType(NarrowKeys.valueOf(nKey.toString()))

                                viewModel.narrowDownVisibleMembers(nKey)
                            }
                        ) {
                            Text(
                                text = nKey.jname,
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}
