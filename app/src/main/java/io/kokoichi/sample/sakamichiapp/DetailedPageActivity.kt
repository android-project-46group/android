package io.kokoichi.sample.sakamichiapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter


@Preview
@Composable
fun DetailedViewPreview() {
    var person = MemberProps(
        name = "iwamotorenka",
        name_ja = "岩本蓮加",
        birthday = "2004年2月2日",
        heigt = "159cm"
    )
    Column {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = null,
        )
        Infos(person = person)
    }
}

@Composable
fun DetailedView(person: MemberProps, navController: NavHostController) {
    Column(modifier = Modifier.clickable {
        navController.navigate("test/user=hogehoge")
    }) {
        val imgUrl = IMG_URL_PREFIFX + SLASH_ENCODED + person.group + SLASH_ENCODED + person.name + IMG_URL_SUFFIX
        Image(
            painter = rememberImagePainter(imgUrl),  // これには size が必要！
            contentDescription = null,
            // TODO.size が必要だったから設定した値であり、ハードコーディングを避ける！ -----
            modifier = Modifier
                .size(600.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )

        Infos(person = person)
    }
}

@Composable
fun Infos(person: MemberProps) {
    Row() {

    }
    Text(
        text = person.name!!,
        modifier = Modifier.padding(all = 4.dp),
        style = MaterialTheme.typography.subtitle2,
    )
    Text(
        text = person.name_ja!!,
        modifier = Modifier.padding(all = 4.dp),
        style = MaterialTheme.typography.subtitle2,
    )
    Text(
        text = person.heigt!!,
        modifier = Modifier.padding(all = 4.dp),
        style = MaterialTheme.typography.subtitle2,
    )
}
