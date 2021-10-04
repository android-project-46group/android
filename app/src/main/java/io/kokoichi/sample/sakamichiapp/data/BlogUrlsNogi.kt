package io.kokoichi.sample.sakamichiapp.data

import android.util.Log
import io.kokoichi.sample.sakamichiapp.models.article
import io.kokoichi.sample.sakamichiapp.models.urls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


/**
 * memberName = renka.iwamoto の形式
 */
fun scrapingImgsNogizaka(memberName: String) = runBlocking {
    var topContent = ""

    var imgUrls = mutableListOf<String>()
    var articles = mutableListOf<article>()
    var urlsList = mutableListOf<urls>()
    withContext(Dispatchers.Default) {
        val document = Jsoup.connect("https://blog.nogizaka46.com/$memberName/").get()

        val titleH1s = document.select("h1.clearfix")

        for (titleH1 in titleH1s) {
            val aTag = titleH1.select("a").first()

            articles.add(
                article(
                    title = aTag.text(),
                    url = aTag.attr("href").replace("http://", "https://")
                )
            )
        }


        for (article in articles) {

            var doc1 = Jsoup.connect(article.url).get()

            var doc2 = doc1.select(".entrybody")
            val imgTags = doc2.select("img")
            // MAYBE: 一つの記事からは写真2枚までにする？（Total 5枚という制約を設ける？）
            // 人によっては横に並びすぎる問題

            var url = ""
            for (imgTag in imgTags) {
                url = imgTag.attr("src").replace("http://", "https://")
                if (url != "https://img.nogizaka46.com/blog/img/dot.gif") {
                    imgUrls.add(url)

                    urlsList.add(
                        urls(
                            imgUrl = url,
                            contentUrl = article.url
                        )
                    )
                }
            }
        }

        Log.d("TAG", urlsList.toString())
    }
    return@runBlocking urlsList
}