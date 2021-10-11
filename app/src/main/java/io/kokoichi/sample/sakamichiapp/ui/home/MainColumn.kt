package io.kokoichi.sample.sakamichiapp.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.kokoichi.sample.sakamichiapp.*
import io.kokoichi.sample.sakamichiapp.models.MemberPayload
import io.kokoichi.sample.sakamichiapp.ui.detailedPage.NOGI_TAG_COLOR
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle
import io.kokoichi.sample.sakamichiapp.ui.util.birthdayStrength

@Composable
fun MainColumn(
    uiState: HomeUiState,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    // TODO: 本当に必要か考える
    // 横に並ぶ人の情報をまとめるデータクラス
    data class _persons(val person1: Member, val person2: Member? = null)

    val db = Firebase.firestore

    Column {
        // load が完了してなければ DB からデータをとってくる
        if (!uiState.loaded) {
            viewModel.setShowStyle(ShowMemberStyle.DEFAULT)

            viewModel.initSortBar()
            viewModel.resetMembers()

            Log.d("TAG", "Download start")

            Log.d(TAG, uiState.groupName)

            // TODO: ここのロジックはどこかに移植する！（data package?）
            db.collection(uiState.groupName).get().addOnSuccessListener { querySnapshot ->
                var tmps = mutableListOf<Member>()
                for (document in querySnapshot) {

                    val userInfo = document.toObject(MemberPayload::class.java)
                    var member = Member(
                        name = userInfo?.name_en!!,
                        name_ja = userInfo.name_ja,
                        birthday = userInfo?.birthday,
                        b_strength = birthdayStrength(userInfo.birthday!!),
                        blog_url = userInfo.blog_url,
                        imgUrl = userInfo.img_url,
                        bloodType = userInfo.blood_type!!,
                        generation = userInfo.generation!!,
                    )

                    tmps.add(member)
                }
                viewModel.addMembers(tmps)
                viewModel.finishLoading()
                Log.d(TAG, "downloader finished")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Exception when retrieving data", exception)
            }
        }

        // Download が終了した時のみ、情報を表示
        if (uiState.loaded) {

            val pairs: MutableList<_persons> = mutableListOf()

            // LazyColumn で items ないでループを回すための準備
            // 全取得の members ではなく、条件により絞り込みをした showingMembers に対して表示を行う
            if (uiState.showStyle == ShowMemberStyle.LINES) {   // Line を引くときは、メンバーの分割を変えてあげる
                var lastVal = ""
                var tmp = 0 // 待ち人数
                var waiting = uiState.showingMembers[0]
                for (person in uiState.showingMembers) {
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
                val numPerson: Int = uiState.showingMembers.size
                val numPair: Int = uiState.showingMembers.size / 2 + uiState.showingMembers.size % 2
                Log.d(TAG, "Number of pairs: $numPair")
                for (i in 0 until numPair) {
                    // 最後の列では、偶数人の時のみ表示させる
                    pairs.add(
                        _persons(
                            person1 = uiState.showingMembers[2 * i],
                            person2 = if (2 * i + 1 < numPerson) {
                                uiState.showingMembers[2 * i + 1]
                            } else {
                                null
                            },
                        )
                    )
                }
            }

            // 途中で離散値を表示する場合の表示
            if (uiState.showStyle == ShowMemberStyle.LINES) {
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
                                groupName = uiState.groupName,
                                showStyle = uiState.showStyle,
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
                            groupName = uiState.groupName,
                            showStyle = uiState.showStyle,
                        )
                    }
                }
            }
        }
    }
}