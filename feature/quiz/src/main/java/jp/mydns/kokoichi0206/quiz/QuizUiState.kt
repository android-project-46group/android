package jp.mydns.kokoichi0206.quiz

import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.GroupName

/**
 * States for Member List UI.
 */
data class QuizUiState(
    var favorites: Set<String> = emptySet(),
    var loaded: Boolean = false,
    var groupName: GroupName? = null,
    var members: MutableList<jp.mydns.kokoichi0206.model.Member> = mutableListOf(),
    var pageType: PageType = PageType.MODE_SELECTION,
    var quizType: QuizType? = null,
    var quizzes: List<Quiz> = emptyList(),
    var quizNum: Int = Constants.MAX_QUIZ_COUNT,
    var quizProgress: Int = 0,
    var scores: Int = 0,
    var isCorrect: Boolean? = null,
    /**
     * Should show answer below the quiz sentence.
     */
    var isAnsShown: Boolean = false,
)

/**
 * Page type to display
 */
enum class PageType {
    /**
     * Choosing game page.
     */
    MODE_SELECTION,

    /**
     * Actual quiz playing page.
     */
    QUIZ,

    /**
     * Results page.
     */
    RESULT,
}

/**
 * Keys to sort members.
 */
enum class QuizType(val jname: String) {
    GENERATION("期別"),
    BLOOD_TYPE("血液型"),
    BIRTHDAY("生年月日"),
    HEIGHT("身長"),
}

/**
 * Data class for one quiz.
 */
data class Quiz(
    val correctMember: jp.mydns.kokoichi0206.model.Member,
    val choices: List<String>,
)

