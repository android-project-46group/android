package io.kokoichi.sample.sakamichiapp.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.kokoichi.sample.sakamichiapp.*
import io.kokoichi.sample.sakamichiapp.models.GroupNames
import io.kokoichi.sample.sakamichiapp.models.MemberPayload
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.NOGI_TAG_COLOR
import io.kokoichi.sample.sakamichiapp.ui.mockGroups
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle
import io.kokoichi.sample.sakamichiapp.ui.util.birthdayStrength
import io.kokoichi.sample.sakamichiapp.ui.util.sortShowingMembers

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
                    } else if (gSelectedGroupName == "sakurazaka") {
                        narLists = SAKURA_NARROW_VALS
                    } else {
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