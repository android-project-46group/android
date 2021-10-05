package io.kokoichi.sample.sakamichiapp.data

import android.util.Log
import io.kokoichi.sample.sakamichiapp.models.article
import io.kokoichi.sample.sakamichiapp.models.urls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


fun scrapingImgsHinata(blogUrl: String) = runBlocking {

    Log.d("SCRAPING", "blog url is " + blogUrl)
    var urlsList = mutableListOf<urls>()
    withContext(Dispatchers.Default) {
        val document = Jsoup.connect(blogUrl).get()

        val articles = document.select("div.p-blog-article")

        for (article in articles) {

            var spans = article.select("span")

            var url = ""
            for (span in spans) {
                var imgTags = span.select("img")
                for (imgTag in imgTags) {
                    url = imgTag.attr("src").replace("http://", "https://")
                    urlsList.add(
                        urls(
                            imgUrl = url,
                            contentUrl = blogUrl
                        )
                    )
                }
            }
        }
    }
    return@runBlocking urlsList
}