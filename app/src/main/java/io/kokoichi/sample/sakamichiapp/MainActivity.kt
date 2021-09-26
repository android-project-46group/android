package io.kokoichi.sample.sakamichiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import io.kokoichi.sample.sakamichiapp.models.MemberPayload
import io.kokoichi.sample.sakamichiapp.ui.GroupList
import io.kokoichi.sample.sakamichiapp.ui.SortBar
import io.kokoichi.sample.sakamichiapp.ui.theme.SakamichiAppTheme


val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            SakamichiAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    Column {
        GroupList()
        SortBar()
        MembersList("nogizaka")
    }
}

data class Member(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val imgUrl: String? = null,
)

//@Preview
//@Composable
//fun HomePreview() {
//    SakamichiAppTheme {
//        Column {
//            GroupList()
//            MembersList("Android", membersFromStorage)
//        }
//    }
//}


// 表示するメンバーオブジェクトの一覧
// あえてあえて、グローバル変数で？持たせている
var members = mutableListOf<Member>()

@Composable
fun MembersList(
    groupName: String,
) {
    // TODO: 本当に必要か考える
    // 横に並ぶ人の情報をまとめるデータクラス
    data class _persons(val person1: Member, val person2: Member? = null)

    // 通信が終わったことを通知するための変数
    var isDownloaded by remember { mutableStateOf(false) }


    val db = Firebase.firestore

    if (!isDownloaded) {
        // Firebase の用意する、非同期通信が終わったことを表すメソッド addOnSuccessListener について、
        // 別メソッドに切り出そうとしたら、その通知を受け取れなくて断念してこの関数内に記述している。
        db.collection(groupName).get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                // TODO: メンバーリストに追加する
                val userInfo = document.toObject(MemberPayload::class.java)

                Log.d("MainActivity", "response is $userInfo")

                var member = Member(
                    name = userInfo?.name_en!!,
                    name_ja = userInfo.name_ja,
                    birthday = userInfo?.birthday,
                    imgUrl = userInfo.img_url,
                )

                members.add(member)
            }
            Log.d(TAG, "downloader finished")
            isDownloaded = true

        }.addOnFailureListener { exception ->
            Log.e(TAG, "Exception when retrieving game", exception)
        }
    }


    // Download が終了した時のみ、情報を表示
    if (isDownloaded) {
        val pairs: MutableList<_persons> = mutableListOf()

        // LazyColumn で items ないでループを回すための準備
        val numPerson: Int = members.size
        val numPair: Int = members.size / 2 + members.size % 2
        Log.d(TAG, "Number of pairs: $numPair")
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
    Column() {
        if (person.imgUrl == null) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Image(
                painter = rememberImagePainter(person.imgUrl),  // これには size が必要！
                contentDescription = null,
                // TODO.size が必要だったから設定した値であり、ハードコーディングを避ける！ -----
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
            )
        }

        Text(
            text = person.name_ja!!,
            modifier = Modifier.padding(all = 4.dp),
            style = MaterialTheme.typography.subtitle2,
        )
@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {

        composable("main") { MainView(navController) }

        // userData は Member クラスを Json オブジェクトにして渡してあげる
        composable(
            route = "detailed/userData={userData}",
            arguments = listOf(navArgument("userData") { type = NavType.StringType })
        ) { backStackEntry ->
            // 受け取った時の処理を記述、
            // Json が渡ってくるので、それをオブジェクトに変換する
            Log.d(TAG, "Received: " + backStackEntry)
            Log.d(TAG, "Received: " + backStackEntry.arguments.toString())


            val userJson = backStackEntry.arguments?.getString("userData")

            Log.d(TAG, userJson.toString())
            val MemberProps = Gson().fromJson<MemberProps>(userJson, MemberProps::class.java)
            DetailedView(MemberProps)
        }
    }
}

data class MemberProps(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val group: String? = "nogizaka",
    val heigt: String? = "159cm"
)
