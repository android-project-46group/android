package io.kokoichi.sample.sakamichiapp.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kokoichi.sample.sakamichiapp.*
import io.kokoichi.sample.sakamichiapp.models.*
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle

@Composable
fun SortBar(uiState: HomeUiState, viewModel: HomeViewModel) {
    val BORDER_RADIUS = 2.dp
    val KEY_FONT_SIZE = 10.sp

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = SortKeyVal.SORT_KEY_MESSAGE.str,
                fontSize = KEY_FONT_SIZE
            )
        }

        var sortExpanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f)
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 2.dp),
                onClick = {
                    sortExpanded = true
                }
            ) {
                Text(text = uiState.sortTyle, fontSize = 8.sp)
            }
            DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false

                        viewModel.sortMemberByNameJa()

                        viewModel.setSortType("なし")
                        viewModel.setShowStyle(ShowMemberStyle.DEFAULT)
                    }
                ) {
                    Text("なし")
                }
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false
                        viewModel.sortMemberByNameEn()

                        viewModel.setSortType(SortKeyVal.SORT_VAL_BY_NAME.str)
                        viewModel.setShowStyle(ShowMemberStyle.DEFAULT)
                    }
                ) {
                    Text(SortKeyVal.SORT_VAL_BY_NAME.str)
                }
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false

                        viewModel.sortMemberByBirthday()

                        viewModel.setSortType(SortKeyVal.SORT_VAL_BY_BIRTHDAY.str)
                        viewModel.setShowStyle(ShowMemberStyle.BIRTHDAY)
                    }
                ) {
                    Text(SortKeyVal.SORT_VAL_BY_BIRTHDAY.str)
                }
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false

                        viewModel.sortMemberByMonthDay()

                        viewModel.setSortType("月日")
                        viewModel.setShowStyle(ShowMemberStyle.BIRTHDAY)
                    }
                ) {
                    Text("月日")
                }
                DropdownMenuItem(
                    onClick = {
                        sortExpanded = false

                        viewModel.sortMemberByBloodType()

                        viewModel.setSortType(SortKeyVal.SORT_VAL_BY_BLOOD.str)
                        viewModel.setShowStyle(ShowMemberStyle.LINES)
                    },
                ) {
                    Text(SortKeyVal.SORT_VAL_BY_BLOOD.str)
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = NarrowKeyVal.NARROW_KEY_MESSAGE.str, fontSize = KEY_FONT_SIZE)
        }

        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f)
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 2.dp),
                onClick = {
                    expanded = true
                }
            ) {
                Text(text = uiState.narrowTyle, fontSize = 8.sp)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    onClick = {
                        Log.d(TAG, "no selection")
                        expanded = false

                        // 表示するメンバーを絞らない
                        viewModel.setShowingMembers(uiState.members)

                        // 順番が重要っぽい
                        viewModel.setNarrowType(NarrowKeyVal.NARROW_VAL_NOTHING.str)
                    }
                ) {
                    Text(NarrowKeyVal.NARROW_VAL_NOTHING.str)
                }

                lateinit var narLists: List<NarrowKeyVal>
                if (uiState.groupName == "nogizaka") {
                    narLists = NOGI_NARROW_VALS
                } else if (uiState.groupName == "sakurazaka") {
                    narLists = SAKURA_NARROW_VALS
                } else {
                    narLists = HINATA_NARROW_VALS
                }
                for (narVal in narLists) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false

                            viewModel.sortShowingMembers(narVal.str)
                            viewModel.setNarrowType(narVal.str)
                        }
                    ) {
                        Text(narVal.str)
                    }
                }
            }
        }
    }
}