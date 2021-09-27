package io.kokoichi.sample.sakamichiapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupList() {
    val groups = mockGroups
    val BORDER_COLOR = Color.Gray
    val BORDER_THICKNESS = 2.dp

    val FONT_SIZE = 20.sp
    val FONT_COLOR = Color.Black

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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "乃木坂", fontSize = FONT_SIZE)
            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(BORDER_THICKNESS)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "櫻坂", fontSize = FONT_SIZE)
            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(BORDER_THICKNESS)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "日向坂", fontSize = FONT_SIZE)
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
