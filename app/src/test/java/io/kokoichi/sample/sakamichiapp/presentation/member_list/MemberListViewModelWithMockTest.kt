package io.kokoichi.sample.sakamichiapp.presentation.member_list

import com.google.common.truth.Truth.assertThat
import io.kokoichi.sample.sakamichiapp.common.calcBirthdayOrder
import io.mockk.mockk

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
            assertThat(vMembers[i].birthday).isLessThan(vMembers[i+1].birthday)
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
            assertThat(vMembers[i].birthday).isGreaterThan(vMembers[i+1].birthday)
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
            assertThat(calcBirthdayOrder(vMembers[i].birthday))
                .isLessThan(calcBirthdayOrder(vMembers[i+1].birthday))        }
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
            assertThat(calcBirthdayOrder(vMembers[i].birthday))
                .isGreaterThan(calcBirthdayOrder(vMembers[i+1].birthday))
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
            assertThat(vMembers[i].height).isLessThan(vMembers[i+1].height)
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
            assertThat(vMembers[i].height).isGreaterThan(vMembers[i+1].height)
        }
    }
}