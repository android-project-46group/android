package io.kokoichi.sample.sakamichiapp.presentation.member_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.common.calcBirthdayOrder
import io.kokoichi.sample.sakamichiapp.common.calcMonthDayOrder
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.domain.usecase.get_members.GetMembersUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * ViewModel of MemberList Screen.
 */
@HiltViewModel
open class MemberListViewModel @Inject constructor(
    private val getMembersUseCase: GetMembersUseCase
) : ViewModel() {

    private val _apiState = mutableStateOf(MemberListApiState())
    var apiState: State<MemberListApiState> = _apiState

    private val _uiState = MutableStateFlow(MemberListUiState())
    var uiState: StateFlow<MemberListUiState> = _uiState

    private var hasInitialized = false

    /**
     * Get members from WebAPI and set them as members (in apiState).
     *
     * @param groupName group name (one of the GroupName enum)
     */
    fun getMembers(groupName: GroupName) {
        getMembersUseCase(groupName.name.lowercase()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _apiState.value =
                        MemberListApiState(
                            members = result.data!!
                                .toMutableList()
                                .onEach {
                                    it.group = groupName.jname
                                }
                        )
                    setVisibleMembers(_apiState.value.members)
                }
                is Resource.Error -> {
                    _apiState.value = MemberListApiState(
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _apiState.value = MemberListApiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Sort members and update visible members using _uiState values.
     * Before using this method, set sortKey and sortType.
     */
    fun sortMembers() {
        when (uiState.value.sortType) {
            SortOrderType.ASCENDING -> {
                sortMembersAscending(uiState.value.sortKey)
            }
            SortOrderType.DESCENDING -> {
                sortMembersDescending(uiState.value.sortKey)
            }
        }
    }

    /**
     * Sort in ascending order.
     *
     * @param sortKey
     */
    private fun sortMembersAscending(sortKey: MemberListSortKeys) {
        when (sortKey) {
            MemberListSortKeys.NAME -> {
                _uiState.value.visibleMembers.sortBy { it.name }
            }
            MemberListSortKeys.GENERATION -> {
                _uiState.value.visibleMembers.sortBy { it.generation }
            }
            MemberListSortKeys.BLOOD_TYPE -> {
                _uiState.value.visibleMembers.sortBy { it.bloodType }
            }
            MemberListSortKeys.BIRTHDAY -> {
                _uiState.value.visibleMembers.sortBy { calcBirthdayOrder(it.birthday) }
            }
            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortBy { calcMonthDayOrder(it.birthday) }
            }
            MemberListSortKeys.HEIGHT -> {
                _uiState.value.visibleMembers.sortBy { it.height }
            }
        }
    }

    /**
     * Sort in descending order.
     *
     * @param sortKey
     */
    private fun sortMembersDescending(sortKey: MemberListSortKeys) {
        when (sortKey) {
            MemberListSortKeys.NAME -> {
                _uiState.value.visibleMembers.sortByDescending { it.name }
            }
            MemberListSortKeys.GENERATION -> {
                _uiState.value.visibleMembers.sortByDescending { it.generation }
            }
            MemberListSortKeys.BLOOD_TYPE -> {
                _uiState.value.visibleMembers.sortByDescending { it.bloodType }
            }
            MemberListSortKeys.BIRTHDAY -> {
                _uiState.value.visibleMembers.sortByDescending { calcBirthdayOrder(it.birthday) }
            }
            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortByDescending { calcMonthDayOrder(it.birthday) }
            }
            MemberListSortKeys.HEIGHT -> {
                _uiState.value.visibleMembers.sortByDescending { it.height }
            }
        }
    }

    fun narrowDownVisibleMembers(nKey: NarrowKeys) {
        if (nKey == NarrowKeys.NONE) {
            _uiState.value.visibleMembers = _apiState.value.members
        } else {
            _uiState.value.visibleMembers = _apiState.value.members.filter {
                it.generation == nKey.jname
            }.toMutableList()
        }
    }

    fun hasInitialized(): Boolean {
        return hasInitialized
    }

    fun setHasInitialized(value: Boolean) {
        hasInitialized = value
    }

    fun setVisibleStyle(type: MemberListSortKeys) {
        val visibleMemberType = when (type) {
            MemberListSortKeys.BLOOD_TYPE, MemberListSortKeys.GENERATION ->
                VisibleMemberStyle.LINES
            else ->
                VisibleMemberStyle.DEFAULT
        }
        setVisibleStyle(visibleMemberType)
    }

    fun resetOptions() {
        _uiState.update {
            it.copy(
                visibleStyle = VisibleMemberStyle.DEFAULT,
                sortKey = MemberListSortKeys.NONE,
                sortType = SortOrderType.ASCENDING,
            )
        }
    }

    /**
     * Reset the members (in apiState) using groupName (in uiState).
     */
    fun setApiMembers() {
        getMembers(uiState.value.groupName)

        // TODO: Consider what logic is the best.
        resetOptions()
    }

    fun setGroupName(groupName: GroupName) {
        _uiState.update { it.copy(groupName = groupName) }
    }

    fun setVisibleStyle(style: VisibleMemberStyle) {
        _uiState.update { it.copy(visibleStyle = style) }
    }

    fun setSortKey(key: MemberListSortKeys) {
        _uiState.update { it.copy(sortKey = key) }
    }

    fun setSortType(type: SortOrderType) {
        _uiState.update { it.copy(sortType = type) }
    }

    fun setNarrowType(type: NarrowKeys) {
        _uiState.update { it.copy(narrowType = type) }
    }

    fun setVisibleMembers(members: MutableList<Member>) {
        _uiState.update { it.copy(visibleMembers = members) }
    }

    fun setApiMembers(members: MutableList<Member>) {
        _apiState.value = MemberListApiState(members = members)
    }
}

