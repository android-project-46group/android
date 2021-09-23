package io.kokoichi.sample.sakamichiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.kokoichi.sample.sakamichiapp.ui.theme.SakamichiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SakamichiAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MembersList("nogi")
                }
            }
        }
    }
}

data class Member(
    val name: String,
    val birthday: String,
    val imgUrl: String? = null,
)

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    SakamichiAppTheme {
        MembersList("Android")
    }
}

@Composable
fun MembersList(name: String) {

    // TODO: 本当に必要か考える
    // 横に並ぶ人の情報をまとめるデータクラス
    data class _persons(val person1: Member, val person2: Member? = null)

    // DB から持ってきたメンバー一覧（メンバーの Array）
    val members = mockMembers
    val pairs: MutableList<_persons> = mutableListOf()

    // LazyColumn で items ないでループを回すための準備
    val numPerson: Int = members.size
    val numPair: Int = members.size / 2 + members.size % 2
    Log.d("MainActivity", "Number of pairs: $numPair")
    for (i in 0 until numPair) {
        // 最後の列では、偶数人の時のみ表示させる
        pairs.add(
            _persons(
                person1 = members[2 * i],
                person2 = if (2 * i + 1 < numPerson) {
                    members[2 * i + 1]
                } else {
                    null
                },
            )
        )
    }

    LazyColumn {
        items(pairs) { pair ->
            OneRow(person1 = pair.person1, person2 = pair.person2)
        }
    }
}

@Composable
fun OneRow(person1: Member, person2: Member? = null) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        // weight で分割する割合を指定
        Box(modifier = Modifier.weight(1f)) {
            OnePerson(person = person1)
        }
        // 横並びのカードの距離
        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            // 2人目が存在する時のみ描画
            if (person2 != null) {
                OnePerson(person = person2)
            }
        }
    }
}


@Composable
fun OnePerson(person: Member) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Content profile picture",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = person.name,
            modifier = Modifier.padding(all = 4.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        Text(text = person.birthday)
    }
}


// Mock For Development
val mockMembers = arrayOf<Member>(
    Member(name = "renka", birthday = "2004/02/02"),
    Member(name = "manatsu", birthday = "1993/08/20"),
    Member(name = "ayame", birthday = "2004/06/08"),
    Member(name = "ayane", birthday = "1999/03/05"),
    Member(name = "hinako", birthday = "1996/07/17"),
    Member(name = "mio", birthday = "2002/08/14"),
    Member(name = "nao", birthday = "1999/02/03"),
    Member(name = "mayu", birthday = "1999/01/12"),
    Member(name = "sakura", birthday = "1999/10/03"),
)
