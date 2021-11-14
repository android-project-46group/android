package io.kokoichi.sample.sakamichiapp.presentation.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.Typography
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags

@Composable
fun PlayQuizScreen(
    uiState: QuizUiState,
    viewModel: QuizViewModel
) {
    // Initialize the members when first loaded.
    if (!uiState.loaded) {
        viewModel.setApiMembers()
    }
    if (uiState.loaded) {
        if (uiState.quizzes.isEmpty()) {
            viewModel.createQuizzes()
        }
    }
    if (uiState.quizzes.isNotEmpty()) {
        OneQuiz(
            uiState = uiState,
            viewModel = viewModel,
            quiz = uiState.quizzes[uiState.quizProgress],
        )
    }

    // Error status or Loading status
    val apiState = viewModel.apiState.value
    Box(modifier = Modifier.fillMaxSize()) {
        if (apiState.error.isNotBlank()) {
            Text(
                text = apiState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                style = Typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (apiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(200.dp),
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.isCorrect != null) {
            LaunchedEffect(true) {
                viewModel.changeQuizProgressAfterDelay()
            }
            when (uiState.isCorrect) {
                true ->
                    MyCircle(
                        modifier = Modifier
                            .size(300.dp)
                            .padding(50.dp)
                            .testTag(TestTags.WRONG_CORRECT_ICON),
                    )
                false ->
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(400.dp)
                            .padding(50.dp)
                            .testTag(TestTags.WRONG_CORRECT_ICON),
                    )
            }
        }
    }
}

@Composable
fun OneQuiz(
    uiState: QuizUiState,
    viewModel: QuizViewModel,
    quiz: Quiz,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(quiz.correctMember.imgUrl,
                            builder = {
                                placeholder(
                                    when (uiState.groupName) {
                                        GroupName.NOGIZAKA ->
                                            R.drawable.nogizaka_official_icon
                                        GroupName.SAKURAZAKA ->
                                            R.drawable.sakurazaka_official_icon
                                        GroupName.HINATAZAKA ->
                                            R.drawable.hinata_official_icon
                                        else ->
                                            R.drawable.nogizaka_official_icon
                                    }
                                )
                                listener(
                                    onStart = {
                                        // set your progressbar visible here
                                    },
                                    onSuccess = { request, metadata ->
                                    }
                                )
                            }),
                        contentDescription = "image of ${quiz.correctMember.name}",
                        contentScale = ContentScale.Crop, // crop the image if it's not a square
                        modifier = Modifier
                            .size(120.dp)
                            .padding(15.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                                shape = CircleShape
                            ),
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        modifier = Modifier
                            .testTag(TestTags.PLAY_QUIZ_TITLE),
                        text = stringResource(
                            R.string.quiz_play_title,
                            quiz.correctMember.name,
                            uiState.quizType!!.jname
                        ),
                        fontSize = 36.sp,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .testTag(TestTags.PLAY_QUIZ_ANS),
                    text = stringResource(
                        R.string.quiz_play_correct_answer,
                        viewModel.getAnsByQuizType(quiz.correctMember)
                    ),
                    style = Typography.h4,
                    color = if (uiState.isAnsShown) Color.Red else Color.Transparent,
                )
            }
        }

        quiz.choices.forEachIndexed { index, choice ->
            OneRow(
                index = index + 1, choice = choice,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    if (uiState.isCorrect == null) {

                        if (choice == viewModel.getAnsByQuizType(quiz.correctMember)) {
                            viewModel.countUpScores()
                            viewModel.setIsCorrect(true)
                        } else {
                            viewModel.setIsCorrect(false)
                        }
                    }
                },
                uiState = uiState,
            )
        }
        CustomProgress(uiState = uiState)
        Box(modifier = Modifier.weight(1f)) {
        }
    }
}

@Composable
fun OneRow(
    index: Int,
    choice: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    uiState: QuizUiState?,
) {
    var selected by remember { mutableStateOf(false) }
    if ((uiState?.isCorrect == null) && (selected)) {
        selected = false
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
                if (uiState?.isCorrect == null) {
                    selected = true
                }
            }
            .background(
                color = if (selected) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(20.dp),
            )
            .testTag(TestTags.PLAY_QUIZ_ONE_CHOICE),
        contentAlignment = Alignment.Center,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.quiz_play_choice_index, index),
                style = Typography.h5,
                modifier = Modifier
                    .padding(0.dp)
                    .weight(1f),
                textAlign = TextAlign.End,
            )
            Text(
                text = choice,
                style = Typography.h5,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(3f),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun CustomProgress(uiState: QuizUiState) {
    val progress = (uiState.quizProgress.toFloat() / uiState.quizNum)
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .height(15.dp)
                .padding(vertical = 0.dp)
                .padding(start = 20.dp, end = 10.dp)
                .weight(7f)
                .testTag(TestTags.PLAY_QUIZ_PROGRESS_BAR),
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(
                R.string.quiz_play_progress,
                uiState.quizProgress,
                uiState.quizNum,
            )
        )
    }
}

@Composable
fun MyCircle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 18.dp,
                color = Color.Red,
                shape = CircleShape,
            )
            .fillMaxWidth()
    )
}


@Preview
@Composable
fun TestPreview() {
    val choices = listOf(
        "1333年2月2日",
        "1333年2月6日",
        "1333年6月8日",
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        choices.forEachIndexed { index, choice ->
            OneRow(
                index = index + 1,
                choice = choice,
                modifier = Modifier
                    .weight(1f),
                onClick = {
                },
                uiState = null
            )
        }
    }
}

@Preview
@Composable
fun OneRowPreview() {
    OneRow(
        index = 1,
        choice = "1992年4月7日",
        onClick = {
        },
        uiState = null
    )
}

@Preview
@Composable
fun MyCirclePreview() {
    MyCircle()
}
