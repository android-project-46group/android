package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.PADDING_HORIZONTAL
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.PADDING_VERTICAL
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceTiny

/**
 * Row of tags
 */
@Composable
fun RowTags(
    tags: MutableList<String>
) {
    Row(
        modifier = Modifier
            .padding(PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL, 0.dp)
    ) {
        for (tag in tags) {
            OneTag(tag = tag)
        }
    }
}

/**
 * Definition of a tag (shape, color...)
 */
@Composable
fun OneTag(
    tag: String
) {
    Box(
        modifier = Modifier
            .padding(horizontal = SpaceTiny)
            .clip(RoundedCornerShape(SpaceMedium))
            .background(MaterialTheme.colors.secondary)
    ) {
        Text(
            text = tag,
            modifier = Modifier
                .padding(horizontal = SpaceTiny),
            style = MaterialTheme.typography.h6,
            color = Color.White,
        )
    }
}