package io.kokoichi.sample.sakamichiapp.data

import android.util.Log
import io.kokoichi.sample.sakamichiapp.models.article
import io.kokoichi.sample.sakamichiapp.models.urls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


fun scrapingImgsSakura(blogUrl: String) = runBlocking {

    Log.d("SCRAPING", "blog url is " + blogUrl)

    var urlsList = mutableListOf<urls>()
    val BASE_URL_SAKURA = "https://sakurazaka46.com"
    withContext(Dispatchers.Default) {
        val document = Jsoup.connect(blogUrl).get()

        val contentUL = document.select("ul.com-blog-part")[0]
        val contentlis = contentUL.select("li.box")

        var articleUrls = mutableListOf<String>()
        for (titleli in contentlis) {
            val aTag = titleli.select("a").first()

            articleUrls.add(BASE_URL_SAKURA + aTag.attr("href"))
        }
        Log.d("SCRAPING", " " + articleUrls)

        // 櫻坂はトップページに並ぶ記事が多すぎるので５記事までに絞る
        var articleCount = 0
        for (articleUrl in articleUrls) {

            articleCount += 1
            var doc1 = Jsoup.connect(articleUrl).get()

            var doc2 = doc1.select(".box-article")
            val imgTags = doc2.select("img")

            var url = ""
            for (imgTag in imgTags) {
                url = imgTag.attr("src").replace("http://", "https://")

                urlsList.add(
                    urls(
                        imgUrl = BASE_URL_SAKURA + url,
                        contentUrl = articleUrl
                    )
                )

            }

            if (articleCount >= 5) {
                break
            }
        }

        Log.d("TAG", urlsList.toString())
    }
    return@runBlocking urlsList
}