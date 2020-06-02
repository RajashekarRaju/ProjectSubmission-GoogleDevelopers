package com.developersbreach.developersbreach.repository.network

import com.developersbreach.developersbreach.model.Author
import com.developersbreach.developersbreach.model.Tags
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import kotlin.collections.ArrayList

private const val JSON_OBJECT_TITLE = "title"
private const val JSON_OBJECT_EXCERPT = "excerpt"
private const val ARTICLE_ID = "id"
private const val ARTICLE_TITLE = "rendered"
private const val ARTICLE_POSTED_DATE = "date"
private const val ARTICLE_URL_LINK = "link"
private const val ARTICLE_EXCERPT = "rendered"
private const val ARTICLE_AUTHOR = "author"
private const val ARTICLE_BANNER = "jetpack_featured_media_url"


private const val JSON_OBJECT_AVATAR = "avatar_urls"
private const val AUTHOR_ID = "id"
private const val AUTHOR_NAME = "name"
private const val AUTHOR_DESCRIPTION = "description"
private const val AUTHOR_AVATAR = "96"


private const val TAG_ID = "id"
private const val TAG_NAME = "name"



fun fetchAuthorJsonData(response: String): Author {

    val baseJsonObject = JSONObject(response)

    var id = 0
    if (baseJsonObject.has(AUTHOR_ID)) {
        id = baseJsonObject.getInt(AUTHOR_ID)
    }

    var name: String? = ""
    if (baseJsonObject.has(AUTHOR_NAME)) {
        name = baseJsonObject.getString(AUTHOR_NAME)
    }

    var description: String? = ""
    if (baseJsonObject.has(AUTHOR_DESCRIPTION)) {
        description = baseJsonObject.getString(AUTHOR_DESCRIPTION)
    }

    val jsonObjectAvatar = baseJsonObject.getJSONObject(JSON_OBJECT_AVATAR)

    var avatarLink: String? = ""
    if (jsonObjectAvatar.has(AUTHOR_AVATAR)) {
        avatarLink = jsonObjectAvatar.getString(AUTHOR_AVATAR)
    }

    return Author(id, name!!, description!!, avatarLink!!)
}

fun fetchTagsJsonData(response: String): List<Tags> {

    val articleTags: MutableList<Tags> = ArrayList()

    try {
        val baseJsonArray = JSONArray(response)

        for (i: Int in 0 until baseJsonArray.length()) {
            val baseJsonObject: JSONObject = baseJsonArray.getJSONObject(i)

            var tagId = 0
            if (baseJsonObject.has(TAG_ID)) {
                tagId = baseJsonObject.getInt(TAG_ID)
            }

            var tagName: String? = ""
            if (baseJsonObject.has(TAG_NAME)) {
                tagName = baseJsonObject.getString(TAG_NAME)
            }

            val tags = Tags(tagId, tagName!!)
            articleTags.add(tags)
        }

    } catch (e: Exception) {
        Timber.e("Problem parsing fetchTagsJsonData results")
    }

    return articleTags
}


fun fetchArticleJsonData(response: String?): List<ArticlesNetwork> {

    val articlesNetworkList: MutableList<ArticlesNetwork> = ArrayList()

    try {
        val baseJsonArray = JSONArray(response)

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