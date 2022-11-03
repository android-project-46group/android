package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel of MemberDetail Screen.
 */
@HiltViewModel
class MemberDetailViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemberDetailUiState())
    var uiState: StateFlow<MemberDetailUiState> = _uiState

    fun setMember(member: jp.mydns.kokoichi0206.model.Member) {
        _uiState.update { it.copy(member = member) }
    }

    fun setTags(tags: List<String>) {
        _uiState.update { it.copy(tags = tags.toMutableList()) }
    }
}