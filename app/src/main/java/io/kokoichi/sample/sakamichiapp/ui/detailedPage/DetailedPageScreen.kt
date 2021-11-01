package io.kokoichi.sample.sakamichiapp.ui.detailedPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.ui.components.MemberProps

val NOGI_TAG_COLOR = Color(191, 135, 194)

@Preview
@Composable
fun DetailedViewPreview() {
    var person = MemberProps(
        name = "iwamotorenka",
        name_ja = "岩本蓮加",
        birthday = "2004年2月2日",
        heigt = "159cm",
        generation = "3期生"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Infos(person = person)
    }
}

@Composable
fun DetailedView(person: MemberProps, navController: NavHostController) {

    // この CenterHorizontally は、写真用。タグのことがあるので、Infos では一旦外す
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var imgUrl = person.img_url

        Image(
            painter = rememberImagePainter(imgUrl),  // これには size が必要！
            contentDescription = null,
            // TODO.size が必要だったから設定した値であり、ハードコーディングを避ける！ -----
            modifier = Modifier
                .size(280.dp)
                .padding(0.dp, 15.dp, 0.dp, 0.dp)
                .clickable {
                    /* DO SOMETHING ? */
                }
        )

        Infos(person = person)

        BlogPics(person, navController)
    }
}

val PADDING_HORIZONTAL = 10.dp
val PADDING_VERTICAL = 4.dp

@Composable
fun Infos(person: MemberProps) {

    // 引き続き Column だが、center 揃えをなくすために再び Column で囲ってあげる
    Column() {

        var Tags = arrayOf(person.generation, "かわいい")
        RowTags(Tags)

        // Japanese Name
        Text(
            text = person.name_ja!!,
            modifier = Modifier
                .padding(PADDING_HORIZONTAL * 3, 0.dp, PADDING_HORIZONTAL * 2, PADDING_VERTICAL),
            style = MaterialTheme.typography.h2,
        )

        // Double Divider
        CustomDevider(Color.Blue, 1.dp)
        Spacer(modifier = Modifier.height(3.dp))
        CustomDevider(Color.Blue, 1.dp)

        OneInfo(InfoKeys.HEIGHT.key, person.heigt.toString())
        CustomDevider(Color.Blue, 1.dp)
        OneInfo(InfoKeys.BIRTHDAY.key, person.birthday!!)
        CustomDevider(Color.Blue, 1.dp)
        OneInfo(InfoKeys.BLOODTYPE.key, person.bloodType)
        CustomDevider(Color.Blue, 1.dp)
    }
}

@Composable
fun RowTags(tags: Array<String>) {
    Row(
        modifier = Modifier
            .padding(PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL, 0.dp)
    ) {
        for (tag in tags) {
            Box(
                modifier = Modifier
                    .padding(3.dp, 0.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(NOGI_TAG_COLOR)
            ) {
                Text(
                    text = tag,
                    modifier = Modifier
                        .padding(4.dp, 0.dp),
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun CustomDevider(color: Color, thickness: Dp) {
    Divider(
        modifier = Modifier
            .padding(PADDING_HORIZONTAL, 0.dp),
        color = color, thickness = thickness
    )
}

// Enum
// InfoKeys.BIRTHDAY.name = "BIRTHDAY"
// InfoKeys.BIRTHDAY.key = "生年月日："
enum class InfoKeys(val key: String) {
    NAME("Name："),
    BIRTHDAY("生年月日："),
    HEIGHT("身長："),
    BLOODTYPE("血液型："),
    GENERATION("世代："),
}

@Composable
fun OneInfo(key: String, value: String) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = key,
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
