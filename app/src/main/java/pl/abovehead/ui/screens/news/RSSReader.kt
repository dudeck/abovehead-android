package pl.abovehead.ui.screens.news

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class RSSReader {
    fun fetchData() {
        val nasaRSSUrl = URL("https://www.nasa.gov/rss/dyn/breaking_news.rss")
        val connection = nasaRSSUrl.openConnection()
        val `in` = BufferedReader(
            InputStreamReader(
                connection.getInputStream()
            )
        )
        var inputLine = ""

        while (`in`.readLine().also { inputLine += it } != null) {}
            println(inputLine)
        `in`.close()
    }
}