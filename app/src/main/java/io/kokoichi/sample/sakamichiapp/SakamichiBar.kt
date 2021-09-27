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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kokoichi.sample.sakamichiapp.MainView
import io.kokoichi.sample.sakamichiapp.selectedGroupName

@Preview
@Composable
fun GroupList() {
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
            var selectedGroupNames by remember { mutableStateOf(selectedGroupName) }
            Log.d(TAG, selectedGroupNames.toString())
            for (pre in GroupName.values()) {
                // 選ばれた値であれば、背景色グレーの値を設定する
                if (pre.name == selectedGroupName) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clickable {
                                selectedGroupNames = pre.name
                                selectedGroupName = pre.name
                                Log.d(TAG, "select group $selectedGroupName")
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
                                selectedGroupName = pre.name
                                Log.d(TAG, "select group $selectedGroupName")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = pre.group, fontSize = FONT_SIZE)
                    }
                }
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

// Enum
enum class GroupName(val group: String) {
    nogizaka("乃木坂"),
    sakurazaka("櫻坂"),
    hinatazaka("日向坂"),
}

// Mock For Development
val mockGroups = arrayOf<String>(
    "乃木坂",
    "櫻坂",
    "日向坂",
)
