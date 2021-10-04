package io.kokoichi.sample.sakamichiapp.ui.home

import androidx.lifecycle.*
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for the Home screen
 */
data class HomeUiState(
    val members: List<Member> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val loaded: Boolean = false,
    val groupName: String = "sakurazaka",
    val showStyle: ShowMemberStyle = ShowMemberStyle.DEFAULT,
    val sortTyle: String = "選んでください",
    val narrowTyle: String = "なし",
//    val persons: List<_person> = emptyList()
) {
    /**
     * True if this represents a first load
     */
//    val initialLoad: Boolean
//        get() = posts.isEmpty() && favorites.isEmpty() && errorMessages.isEmpty() && loading
}

class HomeViewModel : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loaded = true))

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

    fun changeGroup() {

    }

    fun initSortBar() {
        _uiState.update {
            it.copy(
                sortTyle = "選んでください",
                narrowTyle = "なし"
            )
        }
    }
}