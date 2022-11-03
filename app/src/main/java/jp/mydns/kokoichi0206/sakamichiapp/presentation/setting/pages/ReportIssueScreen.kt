package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsUiState
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsViewModel
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.common.ui.theme.SpaceTiny
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags

@Composable
fun ReportIssueScreen(
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium),
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall)
                .testTag(TestTags.REPORT_ISSUE_TITLE),
            text = stringResource(R.string.report_issue_title),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(SpaceSmall))

        val boxShape = MaterialTheme.shapes.large
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(boxShape)
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color = uiState.themeType.subColor,
                    shape = boxShape
                )
                .testTag(TestTags.REPORT_ISSUE_BODY),
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(text = stringResource(R.string.report_issue_text_placeholder))
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.DarkGray,
                backgroundColor = Color.White
            ),
            maxLines = Constants.MAX_REPORT_ISSUE_BODY_LINES,
        )

        Spacer(modifier = Modifier.height(SpaceSmall))

        Text(
            modifier = Modifier
                .clickable {
                    // Open dialog if the text is not empty.
                    if (text.isNotEmpty()) {
                        isDialogOpen = true
                    }
                }
                .testTag(TestTags.REPORT_ISSUE_BUTTON),
            text = stringResource(R.string.report_issue_submit_button),
            color = uiState.themeType.baseColor,
            style = MaterialTheme.typography.h6,
        )
    }

    if (isDialogOpen) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = { },
            title = {
                Text(
                    modifier = Modifier
                        .testTag(TestTags.REPORT_ISSUE_DIALOG_TITLE),
                    text = stringResource(R.string.report_issue_dialog_title),
                    color = Color.DarkGray,
                )
            },
            text = {
                Text(
                    modifier = Modifier
                        .testTag(TestTags.REPORT_ISSUE_DIALOG_BODY),
                    text = stringResource(R.string.report_issue_dialog_body),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start
                )
            },
            buttons = {
                val buttonPaddingValue = SpaceMedium
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = buttonPaddingValue),
                ) {
                    val context = LocalContext.current
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(SpaceTiny))
                            .background(uiState.themeType.subColor)
                            .testTag(TestTags.REPORT_ISSUE_DIALOG_OK),
                        onClick = {
                            isDialogOpen = false
                            viewModel.reportIssue(text)
                            text = ""
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.report_issue_dialog_button_ok),
                            color = uiState.themeType.fontColor,
                        )
                    }
                    // Some space same as the start, end and bottom
                    Spacer(modifier = Modifier.width(buttonPaddingValue))
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = uiState.themeType.subColor,
                                shape = RoundedCornerShape(SpaceTiny)
                            )
                            .testTag(TestTags.REPORT_ISSUE_DIALOG_CANCEL),
                        onClick = {
                            isDialogOpen = false
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.report_issue_dialog_button_cancel),
                            color = uiState.themeType.subColor,
                        )
                    }
                }
            },
        )
    }
}
