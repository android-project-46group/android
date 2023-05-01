package jp.mydns.kokoichi0206.member_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.GroupNameInMemberList
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.domain.usecase.get_members.GetMembersUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * ViewModel of MemberList Screen.
 */
@HiltViewModel
open class MemberListViewModel @Inject constructor(
    private val getMembersUseCase: GetMembersUseCase,
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
    fun getMembers(groupName: GroupNameInMemberList, force: Boolean = false) {
        _uiState.update { it.copy(isLoading = true, error = "", visibleMembers = mutableListOf()) }

        when (groupName) {
            GroupNameInMemberList.All -> {
                GroupName.values().forEach { group ->
                    getMembersUseCase(group.name.lowercase()).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                val new = _uiState.value.visibleMembers + result.data!!
                                setVisibleMembers(new.toMutableList())
                                _uiState.update { it.copy(isLoading = false) }
                            }

                            is Resource.Error -> {
                                _apiState.value = MemberListApiState(
                                    error = result.message ?: "An unexpected error occurred."
                                )
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = result.message ?: "An unexpected error occurred."
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _apiState.value = MemberListApiState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            else -> {
                getMembersUseCase(groupName.name.lowercase(), force).onEach { result ->
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
                            _uiState.update { it.copy(isLoading = false) }
                            setVisibleMembers(_apiState.value.members)
                        }

                        is Resource.Error -> {
                            _apiState.value = MemberListApiState(
                                error = result.message ?: "An unexpected error occurred."
                            )
                            _uiState.update {
                                it.copy(isLoading = false, error = result.message ?: "An unexpected error occurred.")
                            }
                        }

                        is Resource.Loading -> {
                            _apiState.value = MemberListApiState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
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
                _uiState.value.visibleMembers.sortBy { jp.mydns.kokoichi0206.common.calcBirthdayOrder(it.birthday) }
            }

            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortBy { jp.mydns.kokoichi0206.common.calcMonthDayOrder(it.birthday) }
            }

            MemberListSortKeys.HEIGHT -> {
                _uiState.value.visibleMembers.sortBy { it.height }
            }

            else -> {}
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
                _uiState.value.visibleMembers.sortByDescending { jp.mydns.kokoichi0206.common.calcBirthdayOrder(it.birthday) }
            }

            MemberListSortKeys.MONTH_DAY -> {
                _uiState.value.visibleMembers.sortByDescending { jp.mydns.kokoichi0206.common.calcMonthDayOrder(it.birthday) }
            }

            MemberListSortKeys.HEIGHT -> {
                _uiState.value.visibleMembers.sortByDescending { it.height }
            }

            else -> {}
        }
    }

    fun narrowDownVisibleMembers(nKey: NarrowKeys) {
        if (nKey == NarrowKeys.NONE) {
            _uiState.value.visibleMembers = _apiState.value.members
        } else {
            val target = when (nKey) {
                NarrowKeys.FIRST_GEN -> "1期生"
                NarrowKeys.SECOND_GEN -> "2期生"
                NarrowKeys.THIRD_GEN -> "3期生"
                NarrowKeys.FORTH_GEN -> "4期生"
                NarrowKeys.FIFTH_GEN -> "5期生"
                else -> null
            }
            target?.run {
                _uiState.value.visibleMembers = _apiState.value.members.filter {
                    it.generation == target
                }.toMutableList()
            }
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
    fun setApiMembers(force: Boolean = false) {
        getMembers(uiState.value.groupName, force)

        // TODO: Consider what logic is the best.
        resetOptions()
    }

    fun setGroupName(groupName: GroupNameInMemberList) {
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

    fun setVisibleMembers(members: MutableList<jp.mydns.kokoichi0206.model.Member>) {
        _uiState.update { it.copy(visibleMembers = members) }
    }

    fun setApiMembers(members: MutableList<jp.mydns.kokoichi0206.model.Member>) {
        _apiState.value = MemberListApiState(members = members)
    }
}

