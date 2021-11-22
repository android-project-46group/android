package io.kokoichi.sample.sakamichiapp.presentation.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorH
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorN
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorS
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.Typography
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags

/**
 * Screen to choose GroupName and QuizType.
 */
@Composable
fun ChooseQuizTypeScreen(uiState: QuizUiState, viewModel: QuizViewModel) {
    // First, choose and set groupName.
    if (uiState.groupName == null) {
        ChooseGroupNamePage(viewModel = viewModel)
    } else {
        // Then, choose and set quizType.
        ChooseQuizTypePage(viewModel = viewModel)
    }
}

@Composable
fun ChooseGroupNamePage(viewModel: QuizViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        val groupNames = GroupName.values()
        groupNames.forEach { groupName ->
            OneGroup(
                groupName = groupName,
                onClick = {
                    viewModel.setGroupName(groupName)
                    viewModel.setLoaded(false)
                },
            )
        }
    }
}

@Composable
fun OneGroup(
    groupName: GroupName,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 20.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp),
                )
                .testTag(groupName.name),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .size(100.dp)
                    .padding(15.dp),
                painter = painterResource(
                    id = when (groupName) {
                        GroupName.NOGIZAKA ->
                            R.drawable.nogizaka_official_icon
                        GroupName.SAKURAZAKA ->
                            R.drawable.sakurazaka_official_icon
                        GroupName.HINATAZAKA ->
                            R.drawable.hinata_official_icon
                    }
                ),
                contentDescription = groupName.name,
            )
            Text(
                modifier = Modifier
                    .weight(2f),
                text = stringResource(R.string.choose_group_name_text, groupName.jname),
                style = Typography.h4,
                color = when (groupName) {
                    GroupName.NOGIZAKA ->
                        BaseColorN
                    GroupName.SAKURAZAKA ->
                        BaseColorS
                    GroupName.HINATAZAKA ->
                        BaseColorH
                }
            )
        }
    }
}

@Composable
fun ChooseQuizTypePage(viewModel: QuizViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .testTag(TestTags.CHOOSE_QUIZ_TYPE_PAGE_TITLE),
            text = stringResource(R.string.choose_quiz_type_title),
            style = Typography.h5,
            textAlign = TextAlign.Center,
        )
        val quizTypes = QuizType.values()
        // Colors to show quizTypes.
        val colors = listOf(
            Color(0.101960786F, 0.0F, 0.8235294F, 1.0F),
            Color(0.83137256F, 0.16470589F, 0.101960786F, 1.0F),
            Color(0.9607843F, 0.5137255F, 0.9607843F, 1.0F),
            Color(0.13333334F, 0.4F, 0.25882354F, 1.0F),
        )
        quizTypes.forEachIndexed { ind, type ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.setQuizType(type)
                            viewModel.setPageType(PageType.QUIZ)
                        }
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp),
                        )
                        .padding(20.dp)
                        .testTag(TestTags.QUIZ_TYPE_CHOICE),
                    text = type.jname,
                    style = Typography.h4,
                    textAlign = TextAlign.Center,
                    color = colors[ind],
                )
            }
        }
    }
}
