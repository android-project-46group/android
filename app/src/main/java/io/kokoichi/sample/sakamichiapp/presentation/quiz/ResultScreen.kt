package io.kokoichi.sample.sakamichiapp.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.*

@Composable
fun ResultScreen(
    uiState: QuizUiState,
    viewModel: QuizViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.results_screen_total_quiz_num, uiState.quizNum),
            style = Typography.h3,
        )
        Text(
            text = stringResource(R.string.results_screen_scores, uiState.scores),
            style = Typography.h2,
            color = Color.Red,
        )
        Spacer(modifier = Modifier.height(SpaceSmall))

        BackToQuizHomeButton(viewModel = viewModel)

        Spacer(modifier = Modifier.height(SpaceSmall))
        var showAns by remember { mutableStateOf(false) }
        Text(
            modifier = Modifier
                .clickable {
                    showAns = !showAns
                },
            text = if (!showAns) {
                stringResource(R.string.quiz_result_show_ans)
            } else {
                stringResource(R.string.quiz_result_hide_ans)
            },
            style = Typography.h6,
        )
        if (showAns) {
            CorrectAnswers(
                uiState = uiState,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun BackToQuizHomeButton(viewModel: QuizViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceHuge)
    ) {
        Text(
            modifier = Modifier
                .background(SubColorN)
                .fillMaxWidth()
                .clickable {
                    viewModel.resetQuizzes()
                }
                .padding(SpaceMedium),
            text = stringResource(R.string.back_to_quiz_top_page),
            style = Typography.h4,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CorrectAnswers(
    uiState: QuizUiState,
    viewModel: QuizViewModel,
) {
    Column {
        uiState.quizzes.forEach { quiz ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceSmall, horizontal = SpaceLarge),
                verticalAlignment = CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = quiz.correctMember.name,
                    style = Typography.body1,
                )
                Text(
                    modifier = Modifier.padding(SpaceTiny),
                    text = stringResource(R.string.separator_between_name_and_ans),
                    style = Typography.body1,
                )
                Text(
                    modifier = Modifier.weight(3f),
                    text = viewModel.getAnsByQuizType(quiz.correctMember),
                    style = Typography.body1,
                )
            }
        }
    }
}
