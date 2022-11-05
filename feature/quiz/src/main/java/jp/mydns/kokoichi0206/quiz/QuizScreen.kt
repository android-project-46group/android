package jp.mydns.kokoichi0206.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.pageType) {
        PageType.MODE_SELECTION ->
            ChooseQuizTypeScreen(uiState, viewModel)
        PageType.QUIZ ->
            PlayQuizScreen(uiState, viewModel)
        PageType.RESULT ->
            ResultScreen(uiState, viewModel)
    }
}
