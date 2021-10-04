package io.kokoichi.sample.sakamichiapp.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.gSelectedGroupName
import io.kokoichi.sample.sakamichiapp.members
import io.kokoichi.sample.sakamichiapp.models.GroupNames


@Composable
fun GroupList(selectedGroupName: MutableState<String>) {
    val TAG = "SakamichiBar"

    val groups = mockGroups
    val BORDER_COLOR = Color.Gray
    val BORDER_THICKNESS = 2.dp

    val FONT_SIZE = 20.sp
    val FONT_COLOR = Color.Black

    val SELECTED_BG_COLOR = Color.LightGray

    // Row の margin が見つからなかったので、外 BOX の padding で対応
    Box(
        modifier = Modifier
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .border(BorderStroke(BORDER_THICKNESS, BORDER_COLOR))
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // グループ名によって色を管理するための変数
            var selectedGroupNames by remember { mutableStateOf(gSelectedGroupName) }
            Log.d(TAG, selectedGroupNames.toString())
            for (pre in GroupNames.values()) {
                // 選ばれた値であれば、背景色グレーの値を設定する
                if (pre.name == gSelectedGroupName) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clickable {
                                selectedGroupNames = pre.name
                                selectedGroupName.apply { pre.name }
                                members = mutableListOf<Member>()
                                Log.d("TAG", "select group $gSelectedGroupName")
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
                                selectedGroupNames = pre.name
                                gSelectedGroupName = pre.name
                                selectedGroupName.apply { pre.name }
                                members = mutableListOf<Member>()
                                Log.d("TAG", "select group $gSelectedGroupName")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = pre.group, fontSize = FONT_SIZE)
                    }
                }
                gSelectedGroupName = selectedGroupNames
                // 最終 Box 以外には区切りとして縦線をひく
                if (pre.name !== "hinatazaka") {
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

// Mock For Development
val mockGroups = arrayOf<String>(
    "乃木坂",
    "櫻坂",
    "日向坂",
)
