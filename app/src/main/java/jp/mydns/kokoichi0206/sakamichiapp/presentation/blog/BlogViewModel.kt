package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Blog
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_blogs.GetBlogsUseCase
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.GroupName
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * ViewModel of MemberList Screen.
 */
@HiltViewModel
open class BlogViewModel @Inject constructor(
    private val getBlogsUseCase: GetBlogsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(BlogUiState())
    var uiState: StateFlow<BlogUiState> = _uiState

    /**
     * Get blogs from WebAPI and set them to state.
     *
     * @param groupName group name (one of the GroupName enum)
     */
    private fun getBlogs(groupName: GroupName) {
        _uiState.update { it.copy(isLoading = true) }
        getBlogsUseCase(groupName.name.lowercase()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setBlogs(result.data!!.toMutableList())
                    _uiState.update { it.copy(isLoading = false) }

                    // Sorting here is the best??
                    sortBlogs()
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred."
                        )
                    }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sortBlogs() {
        if (uiState.value.isSortTime) {
            _uiState.update {
                it.copy(blogs = it.blogs.sortedByDescending { blog ->
                    blog.lastUpdatedAt
                })
            }
        } else {
            _uiState.update {
                it.copy(blogs = it.blogs.sortedBy { blog ->
                    blog.name
                })
            }
        }
    }

    fun toggleIsSortTime() {
        _uiState.update { it.copy(isSortTime = !it.isSortTime) }
    }

    fun setApiBlogs() {
        setBlogs(mutableListOf())
        getBlogs(uiState.value.groupName)
    }

    fun setBlogs(blogs: List<Blog>) {
        _uiState.update { it.copy(blogs = blogs) }
    }

    fun setLoaded(value: Boolean) {
        _uiState.update { it.copy(loaded = value) }
    }

    fun setGroupName(groupName: GroupName) {
        _uiState.update { it.copy(groupName = groupName) }
    }

    fun setIsSortTime(value: Boolean) {
        _uiState.update { it.copy(isSortTime = value) }
    }
}

