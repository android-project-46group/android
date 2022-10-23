package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Skeleton Screen
 * ref: https://mui.com/material-ui/react-skeleton/#variants
 */
@Composable
fun SkeletonMemberScreen() {
    LazyColumn {
        items(8) {
            Row {
                SkeletonPart(110.dp)
                Box(modifier = Modifier.weight(1f))
                SkeletonPart(110.dp)
                Box(modifier = Modifier.weight(1f))
                SkeletonPart(110.dp)
            }
        }
    }
}

@Composable
fun SkeletonPart(
    size: Dp,
) {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition()

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    Column(
        modifier = Modifier
            .width(size)
    ) {
        Spacer(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
    }
}
