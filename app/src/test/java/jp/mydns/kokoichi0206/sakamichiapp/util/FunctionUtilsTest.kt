package jp.mydns.kokoichi0206.sakamichiapp.util

import com.google.common.truth.Truth.assertThat
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.getMilliSecFromLocalTime
import org.junit.Test
import java.time.*

class FunctionUtilsTest {

    @Test
    fun `getCurrentMilliTime() returns correctly`() {
        // Arrange
        val time = LocalTime.of(9, 11, 22, 120_000_000)

        // Act
        val result = getMilliSecFromLocalTime(time = time)

        // Assert
        assertThat(result).isEqualTo(22120)
    }

    @Test
    fun `getCurrentMilliTime() compare two values works correctly for nano second`() {
        // Arrange
        val time1 = LocalTime.of(9, 11, 22, 120_000_000)
        val time2 = LocalTime.of(9, 11, 22, 420_000_000)

        // Act
        val result1 = getMilliSecFromLocalTime(time = time1)
        val result2 = getMilliSecFromLocalTime(time = time2)
        val res = result2 - result1

        // Assert
        assertThat(res).isEqualTo(300)
    }

    @Test
    fun `getCurrentMilliTime() compare two values works correctly for second`() {
        // Arrange
        val time1 = LocalTime.of(9, 11, 21, 120_000_000)
        val time2 = LocalTime.of(9, 11, 22, 120_000_000)

        // Act
        val result1 = getMilliSecFromLocalTime(time = time1)
        val result2 = getMilliSecFromLocalTime(time = time2)
        val res = result2 - result1

        // Assert
        assertThat(res).isEqualTo(1000)
    }
}
