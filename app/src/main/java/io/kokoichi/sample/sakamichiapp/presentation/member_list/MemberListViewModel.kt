package io.kokoichi.sample.sakamichiapp.presentation.member_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.common.calcBirthdayOrder
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
//    private val _uiState = mutableStateOf(MemberListUiState())
//    var uiState: State<MemberListUiState> = _uiState

    /**
     * Get members from WebAPI and set them as members (in apiState).
     *
     * @param groupName group name (lower case is expected.)
     */
    fun getMembers(groupName: String) {
        getMembersUseCase(groupName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _apiState.value =
                        MemberListApiState(members = result.data!!.toMutableList() ?: arrayListOf())
                    setVisibleMembers(_apiState.value.members)
                    Log.d("hoge", _apiState.value.members.toString())
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
                _uiState.value.visibleMembers.sortBy { it.birthday }
            }
            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortBy { calcBirthdayOrder(it.birthday) }
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
                _uiState.value.visibleMembers.sortByDescending { it.birthday }
            }
            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortByDescending { calcBirthdayOrder(it.birthday) }
            }
            MemberListSortKeys.HEIGHT -> {
                _uiState.value.visibleMembers.sortByDescending { it.height }
            }
        }
    }

    /**
     * Reset the members (in apiState) using groupName (in uiState).
     */
    fun setApiMembers() {
        getMembers(uiState.value.groupName.name.lowercase())
    }

    fun setGroupName(sortKey: MemberListSortKeys) {
        _uiState.update { it.copy(sortKey = sortKey) }
//        _uiState.value.sortKey = sortKey
    }

    fun setVisibleStyle(style: VisibleMemberStyle) {
        _uiState.update { it.copy(visibleStyle = style) }
//        _uiState.value.visibleStyle = style
    }

    fun setSortKey(key: MemberListSortKeys) {
        _uiState.update { it.copy(sortKey = key) }
//        _uiState.value.visibleStyle = key
    }

    fun setSortType(type: SortOrderType) {
        _uiState.update { it.copy(sortType = type) }
//        _uiState.value.sortType = type
    }

    fun setNarrowType(type: NarrowKeys) {
        _uiState.update { it.copy(narrowType = type) }
//        _uiState.value.narrowType = type
    }

    fun setVisibleMembers(members: MutableList<Member>) {
        _uiState.update { it.copy(visibleMembers = members) }
//        _uiState.value.visibleMembers = members
    }
}

