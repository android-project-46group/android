package jp.mydns.kokoichi0206.sakamichiapp.common

/**
 * Function to convert birthday to integer to sort members.
 * Count from 1990
 * 2000年4月18日 → (2000-1990) * 10000 + 4 * 100 + 18 = 100418
 *
 * When the invalid birthday string is passed, this function returns 0.
 */
fun calcBirthdayOrder(birthdayStr: String): Int {

    val expectedBirthdayReg = """(\d{4})年(\d{1,2})月(\d{1,2})日""".toRegex()
    val matchResult = expectedBirthdayReg.matchEntire(birthdayStr)

    return if (matchResult == null || matchResult.groupValues.size != 4) {
        0
    } else {
        val year = matchResult.groupValues!![1].toInt()
        val month = matchResult.groupValues!![2].toInt()
        val day = matchResult?.groupValues!![3].toInt()
        (year - 1990) * 10000 + 100 * month + day
    }
}

/**
 * Function to convert "month and day" to integer to sort members.
 * 2000年4月18日 → 4 * 100 + 18 = 418
 *
 * When the invalid birthday string is passed, this function returns 0.
 */
fun calcMonthDayOrder(birthdayStr: String): Int {

    val expectedBirthdayReg = """(\d{4})年(\d{1,2})月(\d{1,2})日""".toRegex()
    val matchResult = expectedBirthdayReg.matchEntire(birthdayStr)

    return if (matchResult == null || matchResult.groupValues.size != 4) {
        0
    } else {
        val month = matchResult.groupValues!![2].toInt()
        val day = matchResult?.groupValues!![3].toInt()
        100 * month + day
    }
}