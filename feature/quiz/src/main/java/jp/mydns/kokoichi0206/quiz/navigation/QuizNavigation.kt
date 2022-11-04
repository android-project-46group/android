package jp.mydns.kokoichi0206.quiz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.mydns.kokoichi0206.quiz.QuizScreen


const val quizRoute = "quiz_route"

fun NavGraphBuilder.quizScreen() {
    composable(route = quizRoute) {
        QuizScreen()
    }
}
