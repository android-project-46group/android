package jp.mydns.kokoichi0206.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomDevider(color: Color, thickness: Dp) {
    Divider(
        modifier = Modifier
            .padding(10.dp, 0.dp),
        color = color, thickness = thickness
    )
}