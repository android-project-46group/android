package io.kokoichi.sample.sakamichiapp.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle
import io.kokoichi.sample.sakamichiapp.TAG
import io.kokoichi.sample.sakamichiapp.ui.components.MemberProps

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