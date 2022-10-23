package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_positions.GetPositionsUseCase
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_songs.GetSongsUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
open class PositionsViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val getPositionsUseCase: GetPositionsUseCase
) : ViewModel() {
    /**
     * State to store songs api return value.
     */
    private val _songState = mutableStateOf(SongListState())
    var songApiState: State<SongListState> = _songState

    /**
     * State to store positions api return value.
     */
    private val _positionState = mutableStateOf(PositionListState())
    var positionApiState: State<PositionListState> = _positionState

    /**
     * State to display the screen.
     */
    private val _uiState = MutableStateFlow(PositionsUiState())
    var uiState: StateFlow<PositionsUiState> = _uiState

    /**
     * Flag whether viewModel needs to call API.
     */
    private var needApiCall = true

    /**
     * Get songs from WebAPI and set them as state.
     *
     * @param groupName group name
     */
    private fun getSongs(groupName: String) {
        getSongsUseCase(groupName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val songs = result.data ?: emptyList()
                    _songState.value = SongListState(songs = songs)
                    _uiState.value.songs = songs
                }
                is Resource.Error -> {
                    _songState.value = SongListState(
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _songState.value = SongListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get positions from WebAPI and set them as state.
     *
     * @param songName song name
     */
    fun getPositions(songName: String) {
        // Clear first
        _positionState.value = PositionListState(positions = mutableListOf())

        getPositionsUseCase(songName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val positions = result.data ?: emptyList()
                    _positionState.value = PositionListState(positions = positions)
                    setEachRow(positions)
                }
                is Resource.Error -> {
                    _positionState.value = PositionListState(
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _positionState.value = PositionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun setEachRow(positions: List<Position>) {
        _uiState.update { it.copy(firstRow = getFirstRow(positions)) }
        _uiState.update { it.copy(secondRow = getSecondRow(positions)) }
        _uiState.update { it.copy(thirdRow = getThirdRow(positions)) }
    }

    /**
     * Get positions only in the first row.
     * example of first row: 001, 003
     *
     * @param positions List of Position data class
     * @return List of Position data class
     */
    fun getFirstRow(positions: List<Position>): List<Position> {
        return positions
            .filter {
                it.position.substring(2, 3).contains("[1-9]".toRegex())
            }
            .sortedBy {
                it.position
            }
    }

    /**
     * Get positions only in the second row.
     * example of second row: 030, 060
     *
     * @param positions List of Position data class
     * @return List of Position data class
     */
    fun getSecondRow(positions: List<Position>): List<Position> {
        return positions
            .filter {
                it.position.substring(1, 2).contains("[1-9]".toRegex())
            }
            .sortedBy {
                it.position
            }
    }

    /**
     * Get positions only in the third row.
     * example of third row: 200, 600
     *
     * @param positions List of Position data class
     * @return List of Position data class
     */
    fun getThirdRow(positions: List<Position>): List<Position> {
        return positions
            .filter {
                it.position.substring(0, 1).contains("[1-9]".toRegex())
            }
            .sortedBy {
                it.position
            }
    }

    fun getNeedApiCall(): Boolean {
        return needApiCall
    }

    fun setHasInitialized(value: Boolean) {
        needApiCall = value
    }

    /**
     * Get songs(using getSongs func) and set them to state.
     */
    fun setSongs() {
        getSongs("hinatazaka")
    }

    fun setSelectedSong(title: String) {
        _uiState.update { it.copy(selectedSong = title) }
    }
}