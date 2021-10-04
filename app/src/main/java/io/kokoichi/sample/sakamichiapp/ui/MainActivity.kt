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
import io.kokoichi.sample.sakamichiapp.models.GroupNames
import io.kokoichi.sample.sakamichiapp.models.MemberPayload
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.DetailedView
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.NOGI_TAG_COLOR
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.WebViewWidget
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
//                    WebViewWidget(url_short = "https://blog.nogizaka46.com/renka.iwamoto/2021/09/063158.php")
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

// なんか、Composable 変わって戻ってきたら、値が変わっていたので、それを保存するための global 変数...?
var gSelectedShowType = ShowMemberStyle.DEFAULT
var gSelectedGeneration = ""
var gShowingMembers = mutableListOf<Member>()
var gIsDownloaded = false

/**
 * DEFAULT: 何もなし
 * BIRTHDAY: 生年月日を表示
 * LINES: タイプによってラインを引く
 */
enum class ShowMemberStyle {
    DEFAULT, BIRTHDAY, LINES
}

@Composable
fun MainView(groupName: String, navController: NavHostController) {

    var selectedGroupName = remember { mutableStateOf(gSelectedGroupName) }
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
        var isDownloaded by remember { mutableStateOf(gIsDownloaded) }


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
                for (pre in GroupNames.values()) {
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
                                    gIsDownloaded = false
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
                                    selectedGroupName.apply { pre.name }
                                    members = mutableListOf<Member>()
                                    Log.d("TAG", "select group $gSelectedGroupName")
                                    isDownloaded = false
                                    gIsDownloaded = false
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

        // 絞り込みなどによって描画を変える必要があるかどうか
        var needChange by remember { mutableStateOf(false) }

        var showStyle by remember { mutableStateOf(gSelectedShowType) }

        var showMessage by remember { mutableStateOf("") }
        if (showMessage == "") {
            showMessage = SORT_VAL_DEFAULT
        }

        // 絞り込みを行うための設定
        val NARROW_KEY_MESSAGE = "絞り込み"
        val NARROW_VAL_DEFAULT = "選んでください"
        val NARROW_VAL_NOTHING = "なし"
        val NARROW_VAL_FIRST_GEN = "1期生"
        val NARROW_VAL_SECOND_GEN = "2期生"
        val NARROW_VAL_THIRD_GEN = "3期生"
        val NARROW_VAL_FOURTH_GEN = "4期生"

        val NOGI_NARROW_VALS = listOf(
            NARROW_VAL_FIRST_GEN,
            NARROW_VAL_SECOND_GEN,
            NARROW_VAL_THIRD_GEN,
            NARROW_VAL_FOURTH_GEN
        )
        val SAKURA_NARROW_VALS = listOf(NARROW_VAL_FIRST_GEN, NARROW_VAL_SECOND_GEN)
        val HINATA_NARROW_VALS =
            listOf(NARROW_VAL_FIRST_GEN, NARROW_VAL_SECOND_GEN, NARROW_VAL_THIRD_GEN)

        var showMessage2 by remember { mutableStateOf(gSelectedGeneration) }
        if (showMessage2 == "") {
            showMessage2 = NARROW_VAL_DEFAULT
        }

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
                    Text(text = showMessage, fontSize = 8.sp)
                }
                DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            sortExpanded = false
                            showMessage = "なし"
                            showingMembers.sortBy { it.name_ja }
                            showStyle = ShowMemberStyle.DEFAULT
                            gSelectedShowType = ShowMemberStyle.DEFAULT
                            needChange = !needChange
                        }
                    ) {
                        Text("なし")
                    }
                    DropdownMenuItem(
                        onClick = {
                            Log.d(TAG, "sort by name order was clicked")
                            sortExpanded = false
                            showMessage = SORT_VAL_BY_NAME
                            showingMembers.sortBy { it.name }
                            showStyle = ShowMemberStyle.DEFAULT
                            gSelectedShowType = ShowMemberStyle.DEFAULT
                            needChange = !needChange
                        }
                    ) {
                        Text(SORT_VAL_BY_NAME)
                    }
                    DropdownMenuItem(
                        onClick = {
                            sortExpanded = false
                            showMessage = SORT_VAL_BY_BIRTHDAY
                            showingMembers.sortBy { it.birthday }
                            showStyle = ShowMemberStyle.BIRTHDAY
                            gSelectedShowType = ShowMemberStyle.BIRTHDAY
                            needChange = !needChange
                        }
                    ) {
                        Text(SORT_VAL_BY_BIRTHDAY)
                    }
                    DropdownMenuItem(
                        onClick = {
                            sortExpanded = false
                            showMessage = "月日"
                            showingMembers.sortBy { it.b_strength }
                            showStyle = ShowMemberStyle.BIRTHDAY
                            gSelectedShowType = ShowMemberStyle.BIRTHDAY
                            needChange = !needChange
                        }
                    ) {
                        Text("月日")
                    }
                    DropdownMenuItem(
                        onClick = {
                            sortExpanded = false
                            showMessage = SORT_VAL_BY_BLOOD

                            showingMembers.sortBy { it.bloodType }
                            showStyle = ShowMemberStyle.LINES
                            gSelectedShowType = ShowMemberStyle.LINES
                            needChange = !needChange
                        },
                    ) {
                        Text(SORT_VAL_BY_BLOOD)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(2f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = NARROW_KEY_MESSAGE, fontSize = KEY_FONT_SIZE)
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
                    Text(text = showMessage2, fontSize = 8.sp)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            Log.d(TAG, "no selection")
                            expanded = false
                            showMessage2 = NARROW_VAL_NOTHING

                            // 表示するメンバーを絞らない
                            showingMembers = mutableListOf()
                            for (member in members) {
                                showingMembers.add(member)
                            }
                        }
                    ) {
                        Text(NARROW_VAL_NOTHING)
                    }

                    Log.d("TAG", gSelectedGroupName)
                    lateinit var narLists: List<String>
                    if (gSelectedGroupName == "nogizaka") {
                        narLists = NOGI_NARROW_VALS
                    }
                    else if (gSelectedGroupName == "sakurazaka") {
                        narLists = SAKURA_NARROW_VALS
                    }
                    else {
                        narLists = HINATA_NARROW_VALS
                    }
                    for (narVal in narLists) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                showMessage2 = narVal

                                sortShowingMembers(narVal)
                                needChange = !needChange
                            }
                        ) {
                            Text(narVal)
                        }
                    }
//                    DropdownMenuItem(
//                        onClick = {
//                            Log.d(TAG, "1st gen selected")
//                            expanded = false
//                            showMessage2 = NARROW_VAL_FIRST_GEN
//
//                            sortShowingMembers(NARROW_VAL_FIRST_GEN)
//                            needChange = !needChange
//                        }
//                    ) {
//                        Text(NARROW_VAL_FIRST_GEN)
//                    }
//                    DropdownMenuItem(
//                        onClick = {
//                            expanded = false
//                            showMessage2 = NARROW_VAL_SECOND_GEN
//
//                            sortShowingMembers(NARROW_VAL_SECOND_GEN)
//                            needChange = !needChange
//                        }
//                    ) {
//                        Text(NARROW_VAL_SECOND_GEN)
//                    }
//                    DropdownMenuItem(
//                        onClick = {
//                            expanded = false
//                            showMessage2 = NARROW_VAL_THIRD_GEN
//
//                            sortShowingMembers(NARROW_VAL_THIRD_GEN)
//                        },
//                    ) {
//                        Text(NARROW_VAL_THIRD_GEN)
//                    }
//                    DropdownMenuItem(
//                        onClick = {
//                            expanded = false
//                            showMessage2 = NARROW_VAL_FOURTH_GEN
//                            gSelectedGeneration = NARROW_VAL_FOURTH_GEN
//
//                            sortShowingMembers(NARROW_VAL_FOURTH_GEN)
//                        },
//                    ) {
//                        Text(NARROW_VAL_FOURTH_GEN)
//                    }
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

            // グループ切り替えのタイミングで
            gSelectedShowType = ShowMemberStyle.DEFAULT
            showStyle = ShowMemberStyle.DEFAULT
            gSelectedGeneration = ""
            showMessage = SORT_VAL_DEFAULT
            showMessage2 = NARROW_VAL_NOTHING
            gIsDownloaded = false

            members = mutableListOf<Member>()
            showingMembers = mutableListOf<Member>()
            Log.d("TAG", "Download start")
            // Firebase の用意する、非同期通信が終わったことを表すメソッド addOnSuccessListener について、
            // 別メソッドに切り出そうとしたら、その通知を受け取れなくて断念してこの関数内に記述している。
            Log.d(TAG, gSelectedGroupName)
            db.collection(gSelectedGroupName).get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // TODO: メンバーリストに追加する
                    val userInfo = document.toObject(MemberPayload::class.java)

                    Log.d("MainActivity", "response is $userInfo")

                    var member = Member(
                        name = userInfo?.name_en!!,
                        name_ja = userInfo.name_ja,
                        birthday = userInfo?.birthday,
                        b_strength = birthdayStrength(userInfo.birthday!!),
                        imgUrl = userInfo.img_url,
                        bloodType = userInfo.blood_type!!,
                        generation = userInfo.generation!!,
                    )
                    // なぜか 2 回 false のところを通っていたので、
                    // add する前に確認することとす
                    if (!isDownloaded) {
                        members.add(member)
                        showingMembers.add(member)
                    }
                }
                Log.d(TAG, "downloader finished")
                Log.d(TAG, "showingMember size: " + showingMembers.size)
                isDownloaded = true
                gIsDownloaded = true
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Exception when retrieving data", exception)
                Log.e(TAG, "Exception when retrieving data", exception)
            }
        }


        // Download が終了した時のみ、情報を表示
        if (isDownloaded) {
            if (needChange) {
                Log.d("TAG", "change feature worked")
            }
            val pairs: MutableList<_persons> = mutableListOf()

            // LazyColumn で items ないでループを回すための準備
            // 全取得の members ではなく、条件により絞り込みをした showingMembers に対して表示を行う
            if (showStyle == ShowMemberStyle.LINES) {   // Line を引くときは、メンバーの分割を変えてあげる
                var lastVal = ""
                var tmp = 0 // 待ち人数
                var waiting = showingMembers[0]
                for (person in showingMembers) {
                    if (person.bloodType == lastVal) {
                        if (tmp == 0) {
                            tmp = 1
                            waiting = person
                        } else {
                            tmp = 0
                            pairs.add(
                                _persons(
                                    person1 = waiting,
                                    person2 = person
                                )
                            )
                        }
                    } else {
                        lastVal = person.bloodType
                        if (tmp == 0) {
                            tmp = 1
                            waiting = person
                        } else {
                            pairs.add(
                                _persons(
                                    person1 = waiting,
                                    person2 = null
                                )
                            )
                            tmp = 1
                            waiting = person
                        }
                    }
                }
            } else {    // ラインを引かない実装の場合
                val numPerson: Int = showingMembers.size
                val numPair: Int = showingMembers.size / 2 + showingMembers.size % 2
                Log.d(TAG, "Number of pairs: $numPair")
                for (i in 0 until numPair) {
                    // 最後の列では、偶数人の時のみ表示させる
                    pairs.add(
                        _persons(
                            person1 = showingMembers[2 * i],
                            person2 = if (2 * i + 1 < numPerson) {
                                showingMembers[2 * i + 1]
                            } else {
                                null
                            },
                        )
                    )
                }
            }

            // 途中で離散値を表示する場合の表示
            if (showStyle == ShowMemberStyle.LINES) {
                var lastVal = ""
                LazyColumn {
                    var showObj = mutableListOf<_persons>()
                    for (pair in pairs) {
                        if (lastVal != pair.person1.bloodType) {
                            showObj.add(
                                _persons(
                                    person1 = Member(
                                        bloodType = pair.person1.bloodType,
                                        generation = "0期生"
                                    )
                                )
                            )
                            lastVal = pair.person1.bloodType
                        }
                        showObj.add(pair)
                    }
                    items(showObj) { pair ->
                        // TODO: now, only sorted by bloodtype
                        if (pair.person1.generation == "0期生") {
                            Text(
                                text = pair.person1.bloodType,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .background(NOGI_TAG_COLOR)
                                    .fillMaxWidth(),
                                fontSize = 36.sp
                            )
                            lastVal = pair.person1.bloodType
                        } else {
                            OneRow(
                                person1 = pair.person1,
                                person2 = pair.person2,
                                navController = navController,
                                groupName = gSelectedGroupName,
                                showStyle = showStyle,
                            )
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(pairs) { pair ->
                        OneRow(
                            person1 = pair.person1,
                            person2 = pair.person2,
                            navController = navController,
                            groupName = gSelectedGroupName,
                            showStyle = showStyle,
                        )
                    }
                }
            }
        }
    }
}

/**
 * 絞り込みを行う
 * グローバルに関する変数に関しての変更
 */
fun sortShowingMembers(narrowValGen: String) {
    gSelectedGeneration = narrowValGen

    showingMembers = mutableListOf()
    for (member in members) {
        if (member.generation == narrowValGen) {
            showingMembers.add(member)
        }
    }
}

data class Member(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val b_strength: Int? = null,
    val imgUrl: String? = null,
    val height: String? = "159cm",
    val bloodType: String = "不明",
    val generation: String,
)

/**
 * 月日でソートするための関数
 * 2000年4月18日 → 4 * 100 + 18 = 418
 */
fun birthdayStrength(birthday: String): Int {
    var s = birthday.split("年")
    val year = s[0]
    var d = s[1].split("月")
    val month = d[0].toInt()
    val day = d[1].split("日")[0].toInt()
    return 100 * month + day
}


var members = mutableListOf<Member>()
var showingMembers = mutableListOf<Member>()


@Composable
fun OneRow(
    person1: Member,
    person2: Member? = null,
    navController: NavHostController,
    groupName: String,
    showStyle: ShowMemberStyle
) {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        // weight で分割する割合を指定
        Box(modifier = Modifier.weight(1f)) {
            OnePerson(
                person = person1,
                navController = navController,
                groupName = groupName,
                showStyle = showStyle
            )
        }
        // 横並びのカードの距離
        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            // 2人目が存在する時のみ描画
            if (person2 != null) {
                OnePerson(
                    person = person2,
                    navController = navController,
                    groupName = groupName,
                    showStyle = showStyle
                )
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
fun OnePerson(
    person: Member,
    navController: NavHostController,
    groupName: String,
    showStyle: ShowMemberStyle
) {
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
                generation = person.generation,
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
            // Column, Row だと重ならない設定になるので、Box の中に入れてあげる
            Box(
                contentAlignment = Alignment.Center
            ) {
                var finishedLoading by remember { mutableStateOf(false) }
                Image(
                    // rememberImagePainterには size が必要！
                    painter = rememberImagePainter(person.imgUrl,
                        builder = {
                            placeholder(R.drawable.ic_baseline_person_outline_24)
                            listener(
                                onStart = {
                                    // set your progressbar visible here
                                },
                                onSuccess = { request, metadata ->
                                    // set your progressbar invisible here
                                    finishedLoading = true
                                }
                            )
                        }),
                    contentDescription = null,
                    // TODO .size が必要だったから設定した値であり、ハードコーディングを避ける！ -----
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth()
                )
                if (!finishedLoading) {
                    CircularProgressIndicator()
                }
            }
        }

        Column() {
            Text(
                text = person.name_ja!!,
                modifier = Modifier.padding(20.dp, 4.dp, 0.dp, 4.dp),
                style = MaterialTheme.typography.subtitle1,
            )
            // 誕生日ソートがかかっている時のみ、生年月日を表示させる
            if (showStyle == ShowMemberStyle.BIRTHDAY) {
                Text(
                    text = person.birthday!!,
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 4.dp),
                    style = MaterialTheme.typography.subtitle2,
                )
            }
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

        composable(
            route = "webView/url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->

            var url = backStackEntry.arguments?.getString("url")
            if (url == null) {
                url = "https://blog.nogizaka46.com/"
            }
            Log.d("TAG", "The passed content URL is $url")
            WebViewWidget(url)
        }
    }
}

data class MemberProps(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val group: String? = "nogizaka",
    val heigt: String? = "159cm",
    val bloodType: String = "不明",
    val generation: String = "不明"
)
