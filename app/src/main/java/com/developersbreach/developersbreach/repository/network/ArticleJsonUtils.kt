package com.developersbreach.developersbreach.repository.network

import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*


private const val ARTICLE_ID = "id"
private const val ARTICLE_TITLE = "rendered"
private const val JSON_OBJECT_TITLE = "title"


fun fetchArticleJsonData(json: String?): List<ArticlesNetwork> {

    val articlesNetworkList: MutableList<ArticlesNetwork> = ArrayList()

    try {
        // Create a JSONArray from the json response string.
        val baseJsonArray = JSONArray(json)
        // Loop inside each objects of array
        for (i: Int in 0 until baseJsonArray.length()) {
            val baseJsonObject: JSONObject = baseJsonArray.getJSONObject(i)

            // Extract the value for the key called "id"
            var id = 0
            if (baseJsonObject.has(ARTICLE_ID)) {
                id = baseJsonObject.getInt(ARTICLE_ID)
            }

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