package io.kokoichi.sample.sakamichiapp.common

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    /**
     * Function to convert "month and day" to integer to sort members.
     * 2000年4月18日 → 4 * 100 + 18 = 418
     */
    @Test
    fun `calcBirthdayOrder() test, valid birthday returns expected`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "1999年3月5日" to 305,
            "1999年12月17日" to 1217,
            "1998年11月2日" to 1102,
            "2000年2月24日" to 224
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, empty birthday returns 0`() {
        val result = calcBirthdayOrder("")
        assertEquals(0, result)
    }

    @Test
    fun `calcBirthdayOrder() test, invalid year digit returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "19994年3月5日" to 0,
            "200004年12月17日" to 0,
            "199年11月2日" to 0,
            "203年2月24日" to 0,
            "204年2月24日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, invalid year type returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "+994年3月5日" to 0,
            "-1004年12月17日" to 0,
            "+1995年11月2日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, invalid month digit returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "19994年月5日" to 0,
            "2004年012月17日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, invalid month type returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "1994年-3月5日" to 0,
            "2004年+1月17日" to 0,
            "1995年1?月2日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, invalid day digit returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "19994年3月500日" to 0,
            "2004年012月日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }

    @Test
    fun `calcBirthdayOrder() test, invalid day type returns 0`() {
        // input -> expected output
        val map = mapOf<String, Int>(
            "1994年3月-5日" to 0,
            "2004年1月+17日" to 0,
            "1995年1月2??2日" to 0,
        )
        for (key in map.keys) {
            val result = calcBirthdayOrder(key)
            val expected = map[key]
            assertEquals(expected, result)
        }
    }
}