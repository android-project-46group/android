package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.kokoichi.sample.sakamichiapp.domain.usecase.other_api.UpdateBlogUseCase
import io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record.RecordUseCases
import io.kokoichi.sample.sakamichiapp.presentation.quiz.GroupName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateBlogUseCase: UpdateBlogUseCase,
    private val recordUseCase: RecordUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    var uiState: StateFlow<SettingsUiState> = _uiState

    init {
        getAccuracy()
    }

    fun getAccuracy() {
        if (_uiState.value.records.size != 0) {
            return
        }
        viewModelScope.launch {
            val groups = GroupName.values()
            groups.forEach { group ->
                recordUseCase.getAccuracy(
                    group = group.name
                ).let { res ->
                    _uiState.update {
                        it.copy(
                            records = (it.records + Record(
                                group = group.jname,
                                correct = res[0],
                                total = res[1],
                            )).toMutableList()
                        )
                    }
                }
            }
        }
    }

    fun updateBlog() {
        viewModelScope.launch {
            updateBlogUseCase()
        }
    }
}
