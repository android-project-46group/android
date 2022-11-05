package jp.mydns.kokoichi0206.common

import androidx.compose.ui.graphics.Color
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorH
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorN
import jp.mydns.kokoichi0206.common.ui.theme.BaseColorS
import java.time.LocalTime


/**
 * Get current time + millTime
 *
 * @param time LocalTime
 * @return MillSec of current time. (34.122 second -> 34122)
 */
fun getMilliSecFromLocalTime(time: LocalTime): Int {

    return time.second * 1_000 + time.nano / 1_000_000
}

/**
 * Get generation list according to the passed group name.
 *
 * @param group group name
 * @return List of group name (List<String>)
 */
fun getGenerationLooper(group: String): List<String> {
    return when (group) {
        "乃木坂" ->
            Constants.POSSIBLE_GENERATIONS_N
        "櫻坂" ->
            Constants.POSSIBLE_GENERATIONS_S
        "日向坂" ->
            Constants.POSSIBLE_GENERATIONS_H
        else ->
            Constants.POSSIBLE_GENERATIONS_N
    }
}

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
