package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

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
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.ASCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.BIRTHDAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(jp.mydns.kokoichi0206.common.calcBirthdayOrder(vMembers[i].birthday))
                .isLessThanOrEqualTo(jp.mydns.kokoichi0206.common.calcBirthdayOrder(vMembers[i + 1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by birthday descending, correct order`() {
        // Arrange
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.DESCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.BIRTHDAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(jp.mydns.kokoichi0206.common.calcBirthdayOrder(vMembers[i].birthday))
                .isGreaterThanOrEqualTo(jp.mydns.kokoichi0206.common.calcBirthdayOrder(vMembers[i + 1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by (month+day) ascending, correct order`() {
        // Arrange
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.ASCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.MONTH_DAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(jp.mydns.kokoichi0206.common.calcMonthDayOrder(vMembers[i].birthday))
                .isLessThanOrEqualTo(jp.mydns.kokoichi0206.common.calcMonthDayOrder(vMembers[i + 1].birthday))        }
    }

    @Test
    fun `Order visibleMembers by (month+day) descending, correct order`() {
        // Arrange
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.DESCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.MONTH_DAY)

        // Act
        viewModel.sortMembers()

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(jp.mydns.kokoichi0206.common.calcMonthDayOrder(vMembers[i].birthday))
                .isGreaterThanOrEqualTo(jp.mydns.kokoichi0206.common.calcMonthDayOrder(vMembers[i + 1].birthday))
        }
    }

    @Test
    fun `Order visibleMembers by height ascending, correct order`() {
        // Arrange
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.ASCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.HEIGHT)

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
        viewModel.setSortType(jp.mydns.kokoichi0206.member_list.SortOrderType.DESCENDING)
        viewModel.setSortKey(jp.mydns.kokoichi0206.member_list.MemberListSortKeys.HEIGHT)

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
        viewModel.narrowDownVisibleMembers(jp.mydns.kokoichi0206.member_list.NarrowKeys.FIRST_GEN)

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
        viewModel.narrowDownVisibleMembers(jp.mydns.kokoichi0206.member_list.NarrowKeys.FIRST_GEN)

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
        viewModel.narrowDownVisibleMembers(jp.mydns.kokoichi0206.member_list.NarrowKeys.SECOND_GEN)

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
        viewModel.narrowDownVisibleMembers(jp.mydns.kokoichi0206.member_list.NarrowKeys.SECOND_GEN)

        // Assert
        val vMembers = viewModel.uiState.value.visibleMembers
        for (i in 0..vMembers.size - 2) {
            assertThat(vMembers[i].generation).isEqualTo("2期生")
        }
    }
}