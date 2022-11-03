package jp.mydns.kokoichi0206.common

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
