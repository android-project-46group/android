package io.kokoichi.sample.sakamichiapp.ui.home

import androidx.lifecycle.*
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UI state for the Home screen
 */
data class HomeUiState(
    val members: MutableList<Member> = mutableListOf(),
    val favorites: Set<String> = emptySet(),
    val loaded: Boolean = false,
    val groupName: String = "sakurazaka",
    val showStyle: ShowMemberStyle = ShowMemberStyle.DEFAULT,
    val sortTyle: String = "選んでください",
    val narrowTyle: String = "なし",
    var showingMembers: MutableList<Member> = mutableListOf(),
) {
    /**
     * True if this represents a first load
     */
//    val initialLoad: Boolean
//        get() = posts.isEmpty() && favorites.isEmpty() && errorMessages.isEmpty() && loading
}

class HomeViewModel : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loaded = false))

    //    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
    }

    fun setGroup(group: String) {
        _uiState.update { it.copy(groupName = group, loaded = false) }
    }

    fun finishLoading() {
        _uiState.update { it.copy(loaded = true) }
    }

    fun setShowStyle(style: ShowMemberStyle) {
        _uiState.update { it.copy(showStyle = style) }
    }

    fun setNarrowType(type: String) {
        _uiState.update { it.copy(narrowTyle = type) }
    }

    fun setSortType(type: String) {
        _uiState.update { it.copy(sortTyle = type) }
    }


    fun initSortBar() {
        _uiState.update {
            it.copy(
                sortTyle = "選んでください",
                narrowTyle = "なし"
            )
        }
    }

    fun addMembers(members: MutableList<Member>) {
        // メンバーが格納されてない時のみ、追加の処理を行う
        if (uiState.value.members.size != 0) {
            return
        }

        var tmp = mutableListOf<Member>()
        for (member in members) {
            tmp.add(member)
        }
        _uiState.update { it.copy(members = tmp, showingMembers = tmp) }
    }

    fun resetMembers() {
        _uiState.update { it.copy(members = mutableListOf(), showingMembers = mutableListOf()) }
    }

    fun setShowingMembers(memberList: MutableList<Member>) {
        _uiState.update { it.copy(showingMembers = memberList) }
    }

    /**
     * 頑張ってソートするコーナー
     * 一つの関数で頑張りたいところ
     */
    fun sortMemberByNameJa() {
        uiState.value.showingMembers.sortBy { it.name_ja }
    }

    fun sortMemberByNameEn() {
        uiState.value.showingMembers.sortBy { it.name }
    }

    fun sortMemberByBirthday() {
        uiState.value.showingMembers.sortBy { it.birthday }
    }

    fun sortMemberByMonthDay() {
        uiState.value.showingMembers.sortBy { it.b_strength }
    }

    fun sortMemberByBloodType() {
        uiState.value.showingMembers.sortBy { it.bloodType }
    }

    /**
     * 表示するメンバーの絞り込みを行う
     */
    fun sortShowingMembers(gen: String) {

        var showingMembers = mutableListOf<Member>()
        for (member in uiState.value.members) {
            if (member.generation == gen) {
                showingMembers.add(member)
            }
        }
        setShowingMembers(showingMembers)
    }

}