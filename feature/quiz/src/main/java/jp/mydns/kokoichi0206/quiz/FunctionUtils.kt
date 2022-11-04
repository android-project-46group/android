package jp.mydns.kokoichi0206.quiz

import androidx.compose.ui.graphics.Color
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorH
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorN
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorS

/**
 * Get baseColor of the group by group name (NOGIZAKA or 乃木坂)
 *
 * @param group Group name
 * @return BaseColor of the passed group (Color type)
 */
fun getBaseColor(group: String): Color {

    return when (group) {
        GroupName.NOGIZAKA.name, GroupName.NOGIZAKA.jname -> {
            BaseColorN
        }
        GroupName.HINATAZAKA.name, GroupName.HINATAZAKA.jname -> {
            BaseColorH
        }
        GroupName.SAKURAZAKA.name, GroupName.SAKURAZAKA.jname -> {
            BaseColorS
        }
        else -> Color.Black
    }
}

/**
 * Get subColor of the group by group name (NOGIZAKA or 乃木坂)
 *
 * @param group Group name
 * @return SubColor of the passed group (Color type)
 */
fun getSubColor(group: String): Color {

    return when (group) {
        GroupName.NOGIZAKA.name, GroupName.NOGIZAKA.jname -> {
            BaseColorN
        }
        GroupName.HINATAZAKA.name, GroupName.HINATAZAKA.jname -> {
            BaseColorH
        }
        GroupName.SAKURAZAKA.name, GroupName.SAKURAZAKA.jname -> {
            BaseColorS
        }
        else -> BaseColorN
    }
}
