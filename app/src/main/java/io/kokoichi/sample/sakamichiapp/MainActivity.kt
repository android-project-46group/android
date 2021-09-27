package io.kokoichi.sample.sakamichiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.models.MemberPayload
import io.kokoichi.sample.sakamichiapp.ui.GroupList
import io.kokoichi.sample.sakamichiapp.ui.GroupName
import io.kokoichi.sample.sakamichiapp.ui.SortBar
import io.kokoichi.sample.sakamichiapp.ui.mockGroups
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

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG", "onRestart() called")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("TAG", "onBackPressed() called")
    }
}

//var selectedGroupName = "nogizaka"
// globally selected group name
var gSelectedGroupName = "sakurazaka"

@Composable
fun MainView(groupName: String, navController: NavHostController) {

    var selectedGroupName = remember{ mutableStateOf(gSelectedGroupName) }
    selectedGroupName.value = gSelectedGroupName
    Log.d("yoshi", selectedGroupName.toString())

    Column {



        val groups = mockGroups
        val BORDER_COLOR = Color.Gray
        val BORDER_THICKNESS = 2.dp

        val FONT_SIZE = 20.sp
        val FONT_COLOR = Color.Black

        val SELECTED_BG_COLOR = Color.LightGray




        // 通信が終わったことを通知するための変数
        var isDownloaded by remember { mutableStateOf(false) }


        // Row の margin が見つからなかったので、外 BOX の padding で対応
        Box(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .border(BorderStroke(BORDER_THICKNESS, BORDER_COLOR))
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                // グループ名によって色を管理するための変数
                var selectedGroupNames by remember { mutableStateOf(gSelectedGroupName) }
                Log.d(TAG, selectedGroupNames.toString())
                for (pre in GroupName.values()) {
                    // 選ばれた値であれば、背景色グレーの値を設定する
                    if (pre.name == gSelectedGroupName) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clickable {
                                    selectedGroupNames = pre.name
                                    selectedGroupName.apply { pre.name }
                                    members = mutableListOf<Member>()
                                    Log.d("TAG", "select group $gSelectedGroupName")
                                    isDownloaded = false
                                }
                                .background(SELECTED_BG_COLOR),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = pre.group, fontSize = FONT_SIZE)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clickable {
                                    selectedGroupNames = pre.name
                                    gSelectedGroupName = pre.name
                                    selectedGroupName.apply { pre.name }
                                    members = mutableListOf<Member>()
                                    Log.d("TAG", "select group $gSelectedGroupName")
                                    isDownloaded = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = pre.group, fontSize = FONT_SIZE)
                        }
                    }
                    gSelectedGroupName = selectedGroupNames
                    // 最終 Box 以外には区切りとして縦線をひく
                    if (pre.name !== "hinatazaka") {
                        Divider(
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(BORDER_THICKNESS)
                        )
                    }

                }
            }
        }







        val BORDER_RADIUS = 2.dp

        val KEY_FONT_SIZE = 10.sp
        val VALUE_FONT_SIZE = 15.sp

        val SORT_KEY_MESSAGE = "並びかえ"
        val SORT_VAL_DEFAULT = "選んでください"
        val SORT_VAL_BY_NAME = "50音順"
        val SORT_VAL_BY_BIRTHDAY = "生年月日"
        val SORT_VAL_BY_BLOOD = "血液型"


        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()

        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = SORT_KEY_MESSAGE, fontSize = KEY_FONT_SIZE)
            }

            var sortExpanded by remember { mutableStateOf(false) }
            var sortValMessage = SORT_VAL_DEFAULT
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(3f)
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    onClick = {
                        sortExpanded = true
                    }
                ) {
                    Text(text = sortValMessage, fontSize = 8.sp)
                }
                DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            Log.d(TAG, "sort by name order was clicked")
                            sortValMessage = SORT_VAL_BY_NAME
                        }
                    ) {
                        Text(SORT_VAL_BY_NAME)
                    }
                    DropdownMenuItem(
                        onClick = { sortValMessage = SORT_VAL_BY_BIRTHDAY }
                    ) {
                        Text(SORT_VAL_BY_BIRTHDAY)
                    }
                    DropdownMenuItem(
                        onClick = { sortValMessage = SORT_VAL_BY_BLOOD },
                    ) {
                        Text(SORT_VAL_BY_BLOOD)
                    }
                }
            }

            //
            // 絞り込みを行う
            Box(
                modifier = Modifier
                    .weight(2f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "絞り込み", fontSize = KEY_FONT_SIZE)
            }

            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(3f)
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    onClick = {
                        expanded = true
                    }
                ) {
                    Text(text = "選んでください", fontSize = 8.sp)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            Log.d(TAG, "1st gen selected")
                        }
                    ) {
                        Text("1期生")
                    }
                    DropdownMenuItem(
                        onClick = {  }
                    ) {
                        Text("2期生")
                    }
                    DropdownMenuItem(
                        onClick = { /* Handle send feedback! */ },
                    ) {
                        Text("3期生")
                    }
                }
            }
        }






        val gName = selectedGroupName

        // TODO: 本当に必要か考える
        // 横に並ぶ人の情報をまとめるデータクラス
        data class _persons(val person1: Member, val person2: Member? = null)




        val db = Firebase.firestore

        Log.d("TAG", isDownloaded.toString())
        // if (!isDownloaded) {
        if (!isDownloaded) {
            members = mutableListOf<Member>()
            Log.d("TAG", "Download start")
            // Firebase の用意する、非同期通信が終わったことを表すメソッド addOnSuccessListener について、
            // 別メソッドに切り出そうとしたら、その通知を受け取れなくて断念してこの関数内に記述している。
            db.collection(gSelectedGroupName).get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // TODO: メンバーリストに追加する
                    val userInfo = document.toObject(MemberPayload::class.java)

                    Log.d("MainActivity", "response is $userInfo")

                    var member = Member(
                        name = userInfo?.name_en!!,
                        name_ja = userInfo.name_ja,
                        birthday = userInfo?.birthday,
                        imgUrl = userInfo.img_url,
                        bloodType = userInfo.blood_type!!,
                    )
                    // なぜか 2 回 false のところを通っていたので、
                    // add する前に確認することとす
                    if (!isDownloaded) {
                        members.add(member)
                    }
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
                    OneRow(
                        person1 = pair.person1,
                        person2 = pair.person2,
                        navController = navController,
                        groupName = gSelectedGroupName
                    )
                }
            }
        }
    }
}

data class Member(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val imgUrl: String? = null,
    val height: String? = "159cm",
    val bloodType: String = "不明",
)

//@Preview
//@Composable
//fun HomePreview() {
//    SakamichiAppTheme {
//        Column {
//            GroupList()
//            MembersList("Android")
//        }
//    }
//}


// 表示するメンバーオブジェクトの一覧
// あえてあえて、グローバル変数で？持たせている
var members = mutableListOf<Member>()

@Composable
fun MembersList(
    groupName: MutableState<String>,
    navController: NavHostController,
) {



    val gName = groupName.value

    // TODO: 本当に必要か考える
    // 横に並ぶ人の情報をまとめるデータクラス
    data class _persons(val person1: Member, val person2: Member? = null)

    // 通信が終わったことを通知するための変数
    var isDownloaded by remember { mutableStateOf(false) }


    val db = Firebase.firestore

    Log.d("TAG", isDownloaded.toString())
    // if (!isDownloaded) {
    if (!isDownloaded) {
        Log.d("TAG", "Download start")
        // Firebase の用意する、非同期通信が終わったことを表すメソッド addOnSuccessListener について、
        // 別メソッドに切り出そうとしたら、その通知を受け取れなくて断念してこの関数内に記述している。
        db.collection(groupName.value).get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                // TODO: メンバーリストに追加する
                val userInfo = document.toObject(MemberPayload::class.java)

                Log.d("MainActivity", "response is $userInfo")

                var member = Member(
                    name = userInfo?.name_en!!,
                    name_ja = userInfo.name_ja,
                    birthday = userInfo?.birthday,
                    imgUrl = userInfo.img_url,
                    bloodType = userInfo.blood_type!!,
                )
                // なぜか 2 回 false のところを通っていたので、
                // add する前に確認することとす
                if (!isDownloaded) {
                    members.add(member)
                }
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
                OneRow(
                    person1 = pair.person1,
                    person2 = pair.person2,
                    navController = navController,
                    groupName = gName
                )
            }
        }
    }
}

@Composable
fun OneRow(
    person1: Member,
    person2: Member? = null,
    navController: NavHostController,
    groupName: String
) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        // weight で分割する割合を指定
        Box(modifier = Modifier.weight(1f)) {
            OnePerson(person = person1, navController = navController, groupName = groupName)
        }
        // 横並びのカードの距離
        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            // 2人目が存在する時のみ描画
            if (person2 != null) {
                OnePerson(person = person2, navController = navController, groupName = groupName)
            }
        }
    }
}


// imgUrl = IMG_URL_PREFIFX/{groupName}/{memberName}.jpeg?alt=media
val IMG_URL_PREFIFX =
    "https://firebasestorage.googleapis.com/v0/b/my-memory-3b3bd.appspot.com/o/saka"
val IMG_URL_SUFFIX = ".jpeg?alt=media"
val SLASH_ENCODED = "%2F"

@Composable
fun OnePerson(person: Member, navController: NavHostController, groupName: String) {
    Column(
        modifier = Modifier.clickable {
            Log.d(TAG, person.name_ja + " clicked")

            // FIXME: 以下の理由で、MemberProps を作っている。URL に注意
            // Props で渡すときに、URL は JSON デコードがなんか上手くできなかった
            val userProps = MemberProps(
                name = person.name,
                name_ja = person.name_ja,
                birthday = person.birthday,
                group = groupName,
                heigt = person.height,
                bloodType = person.bloodType,
            )

            val jsonUser = Gson().toJson(userProps)
            val ROUTE_MEMBER_DETAILS = "detailed" + "/userData=" + jsonUser
            Log.d(TAG, ROUTE_MEMBER_DETAILS)
            navController.navigate(ROUTE_MEMBER_DETAILS)
        }
    ) {
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

        Row {
            Text(
                text = person.name_ja!!,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun App() {


    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {

        composable("main") { MainView("nogizaka", navController) }

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
            val memberProps = Gson().fromJson<MemberProps>(userJson, MemberProps::class.java)
            DetailedView(memberProps, navController)
        }
    }
}

data class MemberProps(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val group: String? = "nogizaka",
    val heigt: String? = "159cm",
    val bloodType: String = "不明"
)
