package jp.mydns.kokoichi0206.member_list.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.getGenerationLooper
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.common.ui.theme.SpaceHuge
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.common.ui.theme.SpaceTiny
import jp.mydns.kokoichi0206.feature.member_list.R
import jp.mydns.kokoichi0206.member_list.*

/**
 * Bar to change displaying member information.
 */
@Composable
fun SortBar(
    uiState: MemberListUiState,
    onSortClicked: (MemberListSortKeys) -> Unit = {},
    onSortTypeClicked: () -> Unit = {},
    onNarrowClicked: (NarrowKeys) -> Unit = {},
) {
    val context = LocalContext.current

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
                    text = uiState.sortKey.getStringResource(context),
                    fontSize = 8.sp,
                )
            }
        }
        DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
            for (sortKey: MemberListSortKeys in MemberListSortKeys.values()) {
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false
                        onSortClicked(sortKey)
                    }
                ) {
                    Text(
                        text = sortKey.getStringResource(context),
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
                        onSortTypeClicked()
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
                    text = uiState.narrowType.getStringResource(context),
                    fontSize = 8.sp
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                val maxIndex = getGenerationLooper(uiState.groupName.jname).size

                NarrowKeys.values().forEachIndexed { index, nKey ->
                    if (index <= maxIndex) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false

                                onNarrowClicked(nKey)
                            }
                        ) {
                            Text(
                                text = nKey.getStringResource(context),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SortBarPreview() {
    val uiState = MemberListUiState()
    CustomSakaTheme(group = GroupName.NOGIZAKA.jname) {
        Box(modifier = Modifier.background(Color.White)) {
            SortBar(uiState = uiState)
        }
    }
}

@Preview
@Composable
fun SortBarWithSakuraPreview() {
    val uiState = MemberListUiState(
        sortKey = MemberListSortKeys.BIRTHDAY,
    )
    CustomSakaTheme(group = GroupName.SAKURAZAKA.jname) {
        Box(modifier = Modifier.background(Color.White)) {
            SortBar(uiState = uiState)
        }
    }
}
