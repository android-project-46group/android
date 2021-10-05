package io.kokoichi.sample.sakamichiapp.ui.detailedPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.data.scrapingImgsHinata
import io.kokoichi.sample.sakamichiapp.data.scrapingImgsNogi
import io.kokoichi.sample.sakamichiapp.data.scrapingImgsSakura
import io.kokoichi.sample.sakamichiapp.models.urls
import io.kokoichi.sample.sakamichiapp.ui.components.MemberProps
import io.kokoichi.sample.sakamichiapp.ui.util.SLASH_ENCODED

@Composable
fun BlogPics(person: MemberProps, navController: NavHostController) {
    var urlsList = mutableListOf<urls>()

    var blogUrl = person.blog_url

    // TODO: もうちょいスマートにかく？
    if (person.group == "nogizaka") {
        urlsList = scrapingImgsNogi(blogUrl!!)
    } else if (person.group == "sakurazaka") {
        Log.d("SCRAPING", "blog url is " + blogUrl)
        urlsList = scrapingImgsSakura(blogUrl!!)
    } else if (person.group == "hinatazaka") {
        Log.d("SCRAPING", "blog url is " + blogUrl)
        urlsList = scrapingImgsHinata(blogUrl!!)
    }

    LazyRow {
        items(urlsList) { urls ->

            Image(
                painter = rememberImagePainter(urls.imgUrl),  // これには size が必要！
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)  // これ、size と順番が逆だと設定できない
                    .size(200.dp)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .clickable {

                        // navigation の際に / が問題になるのでエスケープする
                        val encodedUrl = urls.contentUrl.replace("/", SLASH_ENCODED)

                        Log.d("TAG", "content URL is: " + encodedUrl)

                        val WEB_VIEW_URL = "webView" + "/url=$encodedUrl"
                        navController.navigate(WEB_VIEW_URL)

                    }
            )
        }
    }
}