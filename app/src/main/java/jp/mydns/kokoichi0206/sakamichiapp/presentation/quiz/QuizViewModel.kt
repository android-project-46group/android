package jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_members.GetMembersUseCase
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.quiz_record.RecordUseCases
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.MemberListApiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of MemberList Screen.
 */
@HiltViewModel
open class QuizViewModel @Inject constructor(
    private val getMembersUseCase: GetMembersUseCase,
    private val recordUseCase: RecordUseCases,
) : ViewModel() {

    private val _apiState = mutableStateOf(MemberListApiState())
    var apiState: State<MemberListApiState> = _apiState

    private val _uiState = MutableStateFlow(QuizUiState())
    var uiState: StateFlow<QuizUiState> = _uiState

    /**
     * Get members from WebAPI and set them as members (in apiState).
     *
     * @param groupName group name (one of the GroupName enum)
     */
    fun getMembers(groupName: GroupName? = GroupName.NOGIZAKA) {
        getMembersUseCase(groupName!!.name.lowercase()).onEach { result ->
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
                    setMembers(_apiState.value.members)
                    _uiState.update { it.copy(loaded = true) }
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
     * Generate random quiz set.
     */
    fun generateQuizzes(): List<Quiz> {
        val quizSize = uiState.value.quizNum
        val choiceSize = 4
        val correctElements = uiState.value.members.asSequence().shuffled().take(quizSize).toList()
        val quizzes = mutableListOf<Quiz>()
        for (i in 0 until quizSize) {
            val correctAns = correctElements[i]
            val choices = uiState.value.members
                .filter { getAnsByQuizType(it) != getAnsByQuizType(correctAns) }
                .map { getAnsByQuizType(it) }
                .distinct()
                .asSequence().shuffled().take(choiceSize - 1).toMutableList()
            choices.add(getAnsByQuizType(correctAns))
            val shuffledChoices = choices.shuffled().toList()

            quizzes.add(
                Quiz(
                    correctMember = correctAns,
                    choices = shuffledChoices,
                )
            )
        }
        return quizzes
    }

    /**
     * Get answer type string from Member by using uiState.value.quizType.
     *
     * @param member Member data class
     * @return One of the information of Member
     */
    fun getAnsByQuizType(member: Member): String {
        return when (uiState.value.quizType) {
            QuizType.BIRTHDAY ->
                member.birthday
            QuizType.HEIGHT ->
                member.height
            QuizType.GENERATION ->
                member.generation
            QuizType.BLOOD_TYPE ->
                member.bloodType
            else ->
                member.birthday
        }
    }

    /**
     * Update quiz record in database.
     */
    private fun updateQuizRecord() {
        viewModelScope.launch {
            // Get the old record if it exists.
            val record = recordUseCase.getRecord(
                group = uiState.value.groupName!!.name,
                type = uiState.value.quizType!!.name,
            )
            // Insert the newer record.
            recordUseCase.insertRecord(
                record.copy(
                    correctNum = record.correctNum + uiState.value.scores,
                    totalNum = record.totalNum + uiState.value.quizNum,
                )
            )
        }
    }

    /**
     * Generate random quizzes and set them to _uiState.
     */
    fun createQuizzes() {
        setQuizzes(generateQuizzes())
    }

    /**
     * Change quiz progress to animate the correct/wrong icon.
     */
    suspend fun changeQuizProgressAfterDelay() {
        viewModelScope.launch {
            if (uiState.value.isCorrect == true) {
                delay(2000L)
            } else {
                delay(1500L)
                // Show the correct answer when the selected is wrong.
                setIsAnsShown(true)
                delay(2500L)
            }
            if (isLastQuiz()) {
                // Update the record quiz total results.
                updateQuizRecord()
                setPageType(PageType.RESULT)
            }
            countUpQuizProgress()
            setIsCorrect(null)
            setIsAnsShown(false)
        }
    }

    /**
     * Check whether the quiz is last one.
     *
     * @return True if the quiz is the last one
     */
    fun isLastQuiz(): Boolean {
        return uiState.value.quizProgress + 1 >= uiState.value.quizNum
    }

    /**
     * Count up the quizProgress.
     */
    private fun countUpQuizProgress() {
        setQuizProgress(_uiState.value.quizProgress + 1)
    }

    /**
     * Count up the scores.
     * Call this function when the selected answer is correct.
     */
    fun countUpScores() {
        setScores(_uiState.value.scores + 1)
    }

    /**
     * Reset UI state related to quizzes.
     */
    fun resetQuizzes() {
        _uiState.update {
            it.copy(
                groupName = null,
                quizType = null,
                pageType = PageType.MODE_SELECTION,
                quizzes = emptyList(),
                quizProgress = 0,
                scores = 0,
            )
        }
    }

    fun setLoaded(value: Boolean) {
        _uiState.update { it.copy(loaded = value) }
    }

    /**
     * Reset the members (in apiState) using groupName (in uiState).
     */
    fun setApiMembers() {
        if (uiState.value.groupName == null) {
            getMembers()
        } else {
            getMembers(uiState.value.groupName)
        }
    }

    fun setGroupName(groupName: GroupName) {
        _uiState.update { it.copy(groupName = groupName) }
    }

    fun setMembers(members: MutableList<Member>) {
        _uiState.update { it.copy(members = members) }
    }

    fun setPageType(type: PageType) {
        _uiState.update { it.copy(pageType = type) }
    }

    fun setQuizType(type: QuizType) {
        _uiState.update { it.copy(quizType = type) }
    }

    fun setQuizzes(quizzes: List<Quiz>) {
        _uiState.update { it.copy(quizzes = quizzes) }
    }

    fun setQuizNum(num: Int) {
        _uiState.update { it.copy(quizNum = num) }
    }

    fun setQuizProgress(num: Int) {
        _uiState.update { it.copy(quizProgress = num) }
    }

    fun setScores(num: Int) {
        _uiState.update { it.copy(scores = num) }
    }

    fun setIsCorrect(value: Boolean?) {
        _uiState.update { it.copy(isCorrect = value) }
    }

    fun setIsAnsShown(value: Boolean) {
        _uiState.update { it.copy(isAnsShown = value) }
    }
}

