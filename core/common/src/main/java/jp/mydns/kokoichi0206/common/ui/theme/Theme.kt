package jp.mydns.kokoichi0206.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.GroupNameInMemberList

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
            GroupNameInMemberList.All.jname -> mixedColors
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

private val mixedColors = lightColors(
    primary = BaseColorMix,
    secondary = SubColorMix,
    background = Color.White,
    primaryVariant = Color.White,
)
