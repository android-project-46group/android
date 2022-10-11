package jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz

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
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.*
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags
import jp.mydns.kokoichi0206.sakamichiapp.R

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
            .padding(horizontal = SpaceLarge, vertical = SpaceMedium),
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
                    shape = RoundedCornerShape(SpaceMedium),
                )
                .testTag(groupName.name),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .size(100.dp)
                    .padding(SpaceMedium),
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
                .padding(SpaceMedium)
                .fillMaxWidth()
                .testTag(TestTags.CHOOSE_QUIZ_TYPE_PAGE_TITLE),
            text = stringResource(R.string.choose_quiz_type_title),
            style = Typography.h5,
            textAlign = TextAlign.Center,
        )
        QuizType.values().forEachIndexed { ind, type ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceLarge, vertical = SpaceSmall),
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
                            shape = RoundedCornerShape(SpaceMedium),
                        )
                        .padding(SpaceMedium)
                        .testTag(TestTags.QUIZ_TYPE_CHOICE),
                    text = type.jname,
                    style = Typography.h4,
                    textAlign = TextAlign.Center,
                    color = Constants.QuizTypeColors[ind],
                )
            }
        }
    }
}
