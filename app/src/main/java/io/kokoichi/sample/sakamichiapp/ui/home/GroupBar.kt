package io.kokoichi.sample.sakamichiapp.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kokoichi.sample.sakamichiapp.models.GroupNames

@Composable
fun GroupBar(
    uiState: HomeUiState,
    viewModel: HomeViewModel,
) {
    // Row の margin が見つからなかったので、外 BOX の padding で対応
    Box(
        modifier = Modifier
            .padding(8.dp),
    ) {
        // Constants
        val BORDER_COLOR = Color.Gray
        val BORDER_THICKNESS = 2.dp
        val SELECTED_BG_COLOR = Color.LightGray
        val FONT_SIZE = 20.sp

        Row(
            modifier = Modifier
                .border(BorderStroke(BORDER_THICKNESS, BORDER_COLOR))
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            for (pre in GroupNames.values()) {
                // 選ばれた値であれば、背景色グレーの値を設定する
                if (pre.name == uiState.groupName) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clickable {

                                viewModel.setGroup(pre.name)
                                viewModel.initSortBar()
                                viewModel.resetMembers()
                                Log.d("TAG", "select group ${pre.name}")
                                Log.d("TAG", "select group ${viewModel.uiState.value.groupName}")

                            }
                            .background(SELECTED_BG_COLOR),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = pre.group, fontSize = FONT_SIZE)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clickable {
                                viewModel.setGroup(pre.name)
                                viewModel.initSortBar()
                                viewModel.resetMembers()
                                Log.d("TAG", "select group ${pre.name}")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = pre.group, fontSize = FONT_SIZE)
                    }
                }
                // 最終 Box 以外には区切りとして縦線をひく
                if (pre.name != "hinatazaka") {
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(BORDER_THICKNESS)
                    )
                }
            }
        }
    }
}