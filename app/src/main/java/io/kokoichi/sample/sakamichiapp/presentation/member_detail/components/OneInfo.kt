package io.kokoichi.sample.sakamichiapp.presentation.member_detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.kokoichi.sample.sakamichiapp.presentation.member_detail.PADDING_HORIZONTAL
import io.kokoichi.sample.sakamichiapp.presentation.member_detail.PADDING_VERTICAL

/**
 * One row information.
 *  ex)   グループ名：乃木坂
 */
@Composable
fun OneInfo(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${key}：",
            modifier = Modifier
                .padding(PADDING_HORIZONTAL, PADDING_VERTICAL)
                .weight(2f),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.End,
        )
        Text(
            text = value,
            modifier = Modifier
                .padding(PADDING_HORIZONTAL, PADDING_VERTICAL)
                .weight(3f),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Start,
        )
    }
}