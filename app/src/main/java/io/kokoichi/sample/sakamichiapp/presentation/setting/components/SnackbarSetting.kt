package io.kokoichi.sample.sakamichiapp.presentation.setting.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.SpaceMedium
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.SpaceSmall
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags

@Composable
fun SnackbarSetting(
    snackbarHostState: SnackbarHostState,
    borderColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
            snackbar = { snackbarData: SnackbarData ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(SpaceMedium)
                        .wrapContentSize()
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(SpaceSmall)
                        )
                        .align(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(SpaceSmall)
                            .testTag(TestTags.SNACK_BAR_TEXT),
                        text = snackbarData.message,
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.DarkGray,
                        ),
                    )
                }
            }
        )
    }
}
