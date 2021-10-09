package io.kokoichi.sample.sakamichiapp.ui.formationPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.kokoichi.sample.sakamichiapp.TAG
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.SLASH_ENCODED


@Composable
internal fun FormationView(navController: NavHostController, viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    
    val db = Firebase.firestore

    var formation = "formations_hinata"
    var title = "ってか"
    // TODO: ここのロジックはどこかに移植する！（data package?）
    db.collection(formation)
        .whereEqualTo("title", title)
        .get()
        .addOnSuccessListener { querySnapshot ->
            Log.d("position", "SUCCESS")
            var tmps = mutableListOf<Position>()
            for (document in querySnapshot) {
                Log.d("position", document.toString())
//                Log.d("position", document.field)

                val position = document.toObject(Position::class.java)
                tmps.add(position)

                Log.d("positoin", position.toString())
            }
            viewModel.setFormations(tmps)
            viewModel.finishLoading()
            

        }.addOnFailureListener { exception ->
            Log.d(TAG, "Exception when retrieving data", exception)
        }

    
    LazyRow {
        items(uiState.formations) { position ->
            
            Text(text = position.name_ja)

//            Image(
//                painter = rememberImagePainter(urls.imgUrl),  // これには size が必要！
//                contentDescription = null,
//                modifier = Modifier
//                    .width(160.dp)  // これ、size と順番が逆だと設定できない
//                    .size(200.dp)
//                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
//                    .clickable {
//
//                        // navigation の際に / が問題になるのでエスケープする
//                        val encodedUrl = urls.contentUrl.replace("/", SLASH_ENCODED)
//
//                        Log.d("TAG", "content URL is: " + encodedUrl)
//
//                        val WEB_VIEW_URL = "webView" + "/url=$encodedUrl"
//                        navController.navigate(WEB_VIEW_URL)
//
//                    }
//            )
        }
    }
}

data class Position(
    val title: String = "不明",
    val single: String = "不明",
    val name_en: String = "不明",
    val name_ja: String = "不明",
    val position: String = "不明",
)