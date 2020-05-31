package com.developersbreach.developersbreach.repository.network

import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*

private const val JSON_OBJECT_TITLE = "title"
private const val JSON_OBJECT_EXCERPT = "excerpt"
private const val JSON_ARRAY_TAGS = "tags"

private const val ARTICLE_ID = "id"
private const val ARTICLE_TITLE = "rendered"
private const val ARTICLE_POSTED_DATE = "date"
private const val ARTICLE_URL_LINK = "link"
private const val ARTICLE_EXCERPT = "rendered"
private const val ARTICLE_AUTHOR = "author"
private const val ARTICLE_BANNER = "jetpack_featured_media_url"


fun fetchArticleJsonData(json: String?): List<ArticlesNetwork> {

    val articlesNetworkList: MutableList<ArticlesNetwork> = ArrayList()

    try {
        val baseJsonArray = JSONArray(json)

        for (i: Int in 0 until baseJsonArray.length()) {
            val baseJsonObject: JSONObject = baseJsonArray.getJSONObject(i)

            val id = 0

            val jsonObject: JSONObject = baseJsonObject.getJSONObject(JSON_OBJECT_TITLE)

            var title: String? = ""
            if (jsonObject.has(ARTICLE_TITLE)) {
                title = jsonObject.getString(ARTICLE_TITLE)
            }

            val articlesNetwork = ArticlesNetwork(id, title!!)
            articlesNetworkList.add(articlesNetwork)
        }

    } catch (e: Exception) {
        Timber.e("Problem parsing fetchArticleJsonData results")
    }

    return articlesNetworkList
}