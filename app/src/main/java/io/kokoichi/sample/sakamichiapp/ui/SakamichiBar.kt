package io.kokoichi.sample.sakamichiapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupList() {
    val groups = mockGroups
    val BORDER_COLOR = Color.Gray
    val BORDER_RADIUS = 2.dp
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(BORDER_RADIUS, BORDER_COLOR))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "乃木坂")
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(BORDER_RADIUS, BORDER_COLOR))
                .padding (4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "櫻坂")
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .border(BorderStroke(BORDER_RADIUS, BORDER_COLOR))
                .padding (4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "日向坂")
        }
    }
}


// Mock For Development
val mockGroups = arrayOf<String>(
    "乃木坂",
    "櫻坂",
    "日向坂",
)
