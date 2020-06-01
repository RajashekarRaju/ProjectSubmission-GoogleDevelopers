package com.developersbreach.developersbreach.repository.network

import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*

private const val JSON_OBJECT_TITLE = "title"
private const val JSON_OBJECT_EXCERPT = "excerpt"

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

            val jsonObjectTitle = baseJsonObject.getJSONObject(JSON_OBJECT_TITLE)
            val jsonObjectExcerpt = baseJsonObject.getJSONObject(JSON_OBJECT_EXCERPT)

            val id: Int = baseJsonArray.length() - i

            var articleId = 0
            if (baseJsonObject.has(ARTICLE_ID)) {
                articleId = baseJsonObject.getInt(ARTICLE_ID)
            }

            var banner: String? = ""
            if (baseJsonObject.has(ARTICLE_BANNER)) {
                banner = baseJsonObject.getString(ARTICLE_BANNER)
            }

            var title: String? = ""
            if (jsonObjectTitle.has(ARTICLE_TITLE)) {
                title = jsonObjectTitle.getString(ARTICLE_TITLE)
            }

            var postedDate: String? = ""
            if (baseJsonObject.has(ARTICLE_POSTED_DATE)) {
                postedDate = baseJsonObject.getString(ARTICLE_POSTED_DATE)
            }

            var urlLink: String? = ""
            if (baseJsonObject.has(ARTICLE_URL_LINK)) {
                urlLink = baseJsonObject.getString(ARTICLE_URL_LINK)
            }

            var excerpt: String? = ""
            if (jsonObjectExcerpt.has(ARTICLE_EXCERPT)) {
                excerpt = jsonObjectExcerpt.getString(ARTICLE_EXCERPT)
            }

            var authorId = 0
            if (baseJsonObject.has(ARTICLE_AUTHOR)) {
                authorId = baseJsonObject.getInt(ARTICLE_AUTHOR)
            }

            val articlesNetwork = ArticlesNetwork(
                id,
                articleId,
                title!!,
                banner!!,
                postedDate!!,
                urlLink!!,
                excerpt!!,
                authorId
            )
            articlesNetworkList.add(articlesNetwork)
        }

    } catch (e: Exception) {
        Timber.e("Problem parsing fetchArticleJsonData results")
    }

    return articlesNetworkList
}