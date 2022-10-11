package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import com.google.common.truth.Truth.assertThat
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position
import io.mockk.mockk
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.Before
import org.junit.Test

class PositionsViewModelWithMockTest {

    private lateinit var viewModel: PositionsViewModelWithMock

    @Before
    fun setUp() {
        viewModel = PositionsViewModelWithMock(mockk(), mockk())
    }

    @Test
    fun `getFirstRow() collects first row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "加藤 史帆",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/katoushiho.jpeg",
                position = "004",
                isCenter = false
            ),
            Position(
                memberName = "齊藤 京子",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/saitoukyouko.jpeg",
                position = "002",
                isCenter = false
            ),
            Position(
                memberName = "東村 芽依",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/higashimuramei.jpeg",
                position = "001",
                isCenter = false
            ),
            Position(
                memberName = "金村 美玖",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kanemuramiku.jpeg",
                position = "003",
                isCenter = true
            ),
        )

        // Act
        val result = viewModel.getFirstRow(items)

        // Assert
        assertThat(result.size).isEqualTo(items.size)
    }

    @Test
    fun `getFirstRow() does not collects second and third row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "佐々木 久美",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakikumi.jpeg",
                position = "010",
                isCenter = false
            ),
            Position(
                memberName = "佐々木 美玲",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakimirei.jpeg",
                position = "040",
                isCenter = false
            ),
            Position(
                memberName = "高瀬 愛奈",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takasemana.jpeg",
                position = "200",
                isCenter = false
            ),
            Position(
                memberName = "高本 彩花",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takamotoayaka.jpeg",
                position = "400",
                isCenter = false
            ),
        )

        // Act
        val result = viewModel.getFirstRow(items)

        // Assert
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `getSecondRow() collects second row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "佐々木 久美",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakikumi.jpeg",
                position = "010",
                isCenter = false
            ),
            Position(
                memberName = "佐々木 美玲",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakimirei.jpeg",
                position = "040",
                isCenter = false
            ),
        )

        // Act
        val result = viewModel.getSecondRow(items)

        // Assert
        assertThat(result.size).isEqualTo(items.size)
    }

    @Test
    fun `getSecondRow() does not collects first and third row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "東村 芽依",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/higashimuramei.jpeg",
                position = "001",
                isCenter = false
            ),
            Position(
                memberName = "金村 美玖",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kanemuramiku.jpeg",
                position = "003",
                isCenter = true
            ),
            Position(
                memberName = "高瀬 愛奈",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takasemana.jpeg",
                position = "200",
                isCenter = false
            ),
            Position(
                memberName = "高本 彩花",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takamotoayaka.jpeg",
                position = "400",
                isCenter = false
            ),
        )

        // Act
        val result = viewModel.getSecondRow(items)

        // Assert
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `getThirdRow() collects third row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "高瀬 愛奈",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takasemana.jpeg",
                position = "200",
                isCenter = false
            ),
            Position(
                memberName = "高本 彩花",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/takamotoayaka.jpeg",
                position = "400",
                isCenter = false
            ),
        )

        // Act
        val result = viewModel.getThirdRow(items)

        // Assert
        assertThat(result.size).isEqualTo(items.size)
    }

    @Test
    fun `getThirdRow() does not collects first and second row`() {
        // Arrange
        val items = listOf<Position>(
            Position(
                memberName = "佐々木 久美",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakikumi.jpeg",
                position = "010",
                isCenter = false
            ),
            Position(
                memberName = "佐々木 美玲",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/sasakimirei.jpeg",
                position = "040",
                isCenter = false
            ),
            Position(
                memberName = "東村 芽依",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/higashimuramei.jpeg",
                position = "001",
                isCenter = false
            ),
            Position(
                memberName = "金村 美玖",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/kanemuramiku.jpeg",
                position = "003",
                isCenter = true
            ),
        )

        // Act
        val result = viewModel.getThirdRow(items)

        // Assert
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `getFirstRow() returns ascending order`() {
        // Arrange
        viewModel.setSampleSongs()

        // Act
        val first = viewModel.getFirstRow(viewModel.positionApiState.value.positions)

        // Assert
        for (i in 0..first.size - 2) {
            AssertionsForClassTypes.assertThat(first[i].position).isLessThan(first[i+1].position)
        }
    }

    @Test
    fun `getSecondRow() returns ascending order`() {
        // Arrange
        viewModel.setSampleSongs()

        // Act
        val second = viewModel.getSecondRow(viewModel.positionApiState.value.positions)

        // Assert
        for (i in 0..second.size - 2) {
            AssertionsForClassTypes.assertThat(second[i].position).isLessThan(second[i+1].position)
        }
    }

    @Test
    fun `getThirdRow() returns ascending order`() {
        // Arrange
        viewModel.setSampleSongs()

        // Act
        val third = viewModel.getThirdRow(viewModel.positionApiState.value.positions)

        // Assert
        for (i in 0..third.size - 2) {
            AssertionsForClassTypes.assertThat(third[i].position).isLessThan(third[i+1].position)
        }
    }
}