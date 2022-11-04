package jp.mydns.kokoichi0206.blog.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.blogImage(): Modifier =
    composed {
        size(120.dp)
            .padding(2.dp)
            .clip(MaterialTheme.shapes.medium)
    }
