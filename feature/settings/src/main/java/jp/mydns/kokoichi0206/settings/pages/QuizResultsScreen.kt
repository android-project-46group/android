package jp.mydns.kokoichi0206.settings.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.common.ui.theme.SpaceHuge
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.common.components.CustomDevider
import jp.mydns.kokoichi0206.common.getBaseColor
import jp.mydns.kokoichi0206.feature.settings.R

@Composable
fun QuizResultsScreen(uiState: SettingsUiState) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ResultTitle()

        ContentsDescription()

        CustomDevider(
            color = Color.LightGray,
            thickness = 2.dp,
        )

        uiState.records.forEach { record ->
            RowTest(
                group = record.group,
                correct = record.correct.toString(),
                total = record.total.toString(),
            )
        }
    }
}

@Composable
fun ResultTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier
                .padding(SpaceHuge),
            text = stringResource(R.string.quiz_result_screen_title),
            style = MaterialTheme.typography.h3,
        )
    }
}

@Composable
fun ContentsDescription() {
    RowTest(
        group = stringResource(R.string.column_name_group),
        correct = stringResource(R.string.column_name_correct),
        total = stringResource(R.string.column_name_total),
    )
}

@Composable
fun RowTest(
    group: String,
    correct: String,
    total: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceSmall, horizontal = SpaceHuge)
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = group,
            style = MaterialTheme.typography.h5,
            color = getBaseColor(group)
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = correct,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.End,
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = total,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.End,
        )
    }
}
