package com.developersbreach.developersbreach.repository.network

import android.net.Uri
import com.developersbreach.developersbreach.repository.database.ArticlesEntity
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


private const val SCHEME_AUTHORITY = "https://developersbreach.com"
private const val APPEND_PATH_JSON = "wp-json"
private const val APPEND_PATH_WP = "wp"
private const val APPEND_PATH_VERSION = "v2"
private const val APPEND_PATH_POSTS = "posts"

@Throws(IOException::class)
fun startResponse(): String {
    val uriString: String = uriBuilder()
    val requestUrl: URL = createUrl(uriString)!!
    return getResponseFromHttpUrl(requestUrl)
}

fun uriBuilder(): String {
    val baseUri: Uri = Uri.parse(SCHEME_AUTHORITY)
    // Constructs a new Builder.
    val uriBuilder: Uri.Builder = baseUri.buildUpon()
    uriBuilder
        .appendPath(APPEND_PATH_JSON)
        .appendPath(APPEND_PATH_WP)
        .appendPath(APPEND_PATH_VERSION)
        .appendPath(APPEND_PATH_POSTS)
    // Returns a string representation of the object.
    return uriBuilder.build().toString()
}

@Throws(IOException::class)
private fun getResponseFromHttpUrl(url: URL): String {
    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
    return try {
        val `in`: InputStream = urlConnection.inputStream
        val scanner = Scanner(`in`)
        scanner.useDelimiter("\\A")
        val hasInput: Boolean = scanner.hasNext()
        var response: String? = null
        if (hasInput) {
            response = scanner.next()
        }
        scanner.close()
        response
    } finally {
        urlConnection.disconnect()
    }!!
}

private fun createUrl(stringUrl: String): URL? {
    var url: URL? = null
    try {
        url = URL(stringUrl)
    } catch (e: MalformedURLException) {
        Timber.e(e)
    }
    return url
}


fun fetchArticleJsonData(json: String?): List<ArticlesEntity> {

    val articlesNetworkList: MutableList<ArticlesEntity> = ArrayList()

    try {
        // Create a JSONArray from the json response string.
        val baseJsonArray = JSONArray(json)
        // Loop inside each objects of array
        for (i: Int in 0 until baseJsonArray.length()) {
            val baseJsonObject = baseJsonArray.getJSONObject(i)

            // Extract the value for the key called "id"
            var id = 0
            if (baseJsonObject.has("id")) {
                id = baseJsonObject.getInt("id")
            }

            val jsonObject: JSONObject = baseJsonObject.getJSONObject("title")
            var title: String? = ""
            if (jsonObject.has("rendered")) {
                title = jsonObject.getString("rendered")
            }

            val articlesNetwork = ArticlesEntity(id, title!!)
            articlesNetworkList.add(articlesNetwork)
        }

    } catch (e: Exception) {
        Timber.e("Problem parsing fetchArticleJsonData results")
    }

    return articlesNetworkList
}