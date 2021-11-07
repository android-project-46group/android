package io.kokoichi.sample.sakamichiapp.presentation.member_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.domain.model.Member

@Composable
fun OnePerson(
    member: Member,
    modifier: Modifier = Modifier,
    onclick: (Member) -> Unit = {},
    extraInfo: String? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable {
                onclick(member)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(member.imgUrl,
                    builder = {
                        placeholder(when(member.group) {
                            "乃木坂" ->
                                R.drawable.nogizaka_official_icon
                            "櫻坂" ->
                                R.drawable.sakurazaka_official_icon
                            else ->
                                R.drawable.hinata_official_icon
                        })
                        listener(
                            onStart = {
                                // set your progressbar visible here
                            },
                            onSuccess = { request, metadata ->
                            }
                        )
                    }),
                contentDescription = "image of ${member.name}",
                contentScale = ContentScale.Crop, // crop the image if it's not a square
                modifier = Modifier
                    .clickable {
                        onclick(member)
                    }
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.secondary, CircleShape),
                alignment = Alignment.TopStart
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = member.name,
                color = MaterialTheme.colors.secondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(0.dp))

            // Extra information (like birthday, height and so on)
            if (extraInfo != null) {
                Text(
                    text = extraInfo,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
