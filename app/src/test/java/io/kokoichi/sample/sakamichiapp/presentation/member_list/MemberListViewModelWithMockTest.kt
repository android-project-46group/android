package io.kokoichi.sample.sakamichiapp.presentation.member_list

import io.kokoichi.sample.sakamichiapp.common.calcBirthdayOrder
import io.kokoichi.sample.sakamichiapp.common.calcMonthDayOrder
import io.mockk.mockk
import org.assertj.core.api.AssertionsForClassTypes.assertThat

import org.junit.Before
import org.junit.After
import org.junit.Test

class MemberListViewModelWithMockTest {

    private lateinit var viewModel: MemberListViewModelWihMock

    @Before
    fun setUp() {
        viewModel = MemberListViewModelWihMock(mockk())

        // Initialize the members
        viewModel.setVisibleMembers(members = viewModel.fakeGetMembersApi())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Order visibleMembers by birthday ascending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.ASCENDING)
        viewModel.setSortKey(MemberListSortKeys.BIRTHDAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(calcBirthdayOrder(vMembers[i].birthday))
                .isLessThanOrEqualTo(calcBirthdayOrder(vMembers[i+1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by birthday descending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.DESCENDING)
        viewModel.setSortKey(MemberListSortKeys.BIRTHDAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(calcBirthdayOrder(vMembers[i].birthday))
                .isGreaterThanOrEqualTo(calcBirthdayOrder(vMembers[i+1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by (month+day) ascending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.ASCENDING)
        viewModel.setSortKey(MemberListSortKeys.MONTH_DAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(calcMonthDayOrder(vMembers[i].birthday))
                .isLessThanOrEqualTo(calcMonthDayOrder(vMembers[i+1].birthday))        }
    }

    @Test
    fun `Order visibleMembers by (month+day) descending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.DESCENDING)
        viewModel.setSortKey(MemberListSortKeys.MONTH_DAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(calcMonthDayOrder(vMembers[i].birthday))
                .isGreaterThanOrEqualTo(calcMonthDayOrder(vMembers[i+1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by height ascending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.ASCENDING)
        viewModel.setSortKey(MemberListSortKeys.HEIGHT)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].height).isLessThanOrEqualTo(vMembers[i+1].height)
        }
    }

    @Test
    fun `Order visibleMembers by height descending, correct order`() {
        // Arrange
        viewModel.setSortType(SortOrderType.DESCENDING)
        viewModel.setSortKey(MemberListSortKeys.HEIGHT)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].height).isGreaterThanOrEqualTo(vMembers[i+1].height)
        }
    }

    @Test
    fun `Narrow down by 1st gen, only 1st gen members`() {
        // Arrange
        viewModel.setApiMembers(members = viewModel.uiState.value.visibleMembers)

        // Act
        viewModel.narrowDownVisibleMembers(NarrowKeys.FIRST_GEN)

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].generation).isEqualTo("1期生")
        }
    }

    @Test
    fun `Narrow down by 1st gen, no 2nd gen members`() {
        // Arrange
        viewModel.setApiMembers(members = viewModel.uiState.value.visibleMembers)

        // Act
        viewModel.narrowDownVisibleMembers(NarrowKeys.FIRST_GEN)

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].generation).isNotEqualTo("2期生")
        }
    }

    @Test
    fun `Narrow down by 2nd gen, only 2nd members`() {
        // Arrange
        viewModel.setApiMembers(members = viewModel.uiState.value.visibleMembers)

        // Act
        viewModel.narrowDownVisibleMembers(NarrowKeys.SECOND_GEN)

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].generation).isEqualTo("2期生")
        }
    }

    @Test
    fun `Narrow down by 2nd gen, no 1st members`() {
        // Arrange
        viewModel.setApiMembers(members = viewModel.uiState.value.visibleMembers)

        // Act
        viewModel.narrowDownVisibleMembers(NarrowKeys.SECOND_GEN)

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].generation).isEqualTo("2期生")
        }
    }
}