package io.kokoichi.sample.sakamichiapp.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.kokoichi.sample.sakamichiapp.presentation.member_list.GroupName

@Composable
fun CustomSakaTheme(
    group: String = "乃木坂",
    content: @Composable() () -> Unit
) {
    // Change color according to the selected group
    MaterialTheme(
        colors = when(group){
            GroupName.NOGIZAKA.jname -> nogiColors
            GroupName.SAKURAZAKA.jname -> sakuraColors
            GroupName.HINATAZAKA.jname -> hinataColors
            else -> nogiColors
        },
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val nogiColors = lightColors(
    primary = BaseColorN,
    secondary = SubColorN,
    background = Color.White,
    primaryVariant = Color.White,
)

private val hinataColors = lightColors(
    primary = BaseColorH,
    secondary = SubColorH,
    background = Color.White,
    primaryVariant = Color.White,
)

private val sakuraColors = lightColors(
    primary = BaseColorS,
    secondary = SubColorS,
    background = Color.White,
    primaryVariant = Color.White,
)