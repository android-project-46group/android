package io.kokoichi.sample.sakamichiapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.ShowMemberStyle

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