package com.developersbreach.developersbreach.repository.network

import android.net.Uri
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


private const val SCHEME_AUTHORITY = "https://developersbreach.com"
private const val APPEND_PATH = "wp-json/wp/v2"
private const val APPEND_ENDPOINT_POSTS = "posts"
private const val APPEND_ENDPOINT_TAGS = "tags"
private const val APPEND_ENDPOINT_USERS = "users/107376512"


fun getArticles(): NetworkArticlesContainer {
    val articlesNetworkList: List<ArticlesNetwork> = fetchArticleJsonData(startResponse())
    return NetworkArticlesContainer(articlesNetworkList)
}

@Throws(IOException::class)
private fun startResponse(): String {
    val uriString: String = uriBuilder()
    val requestUrl: URL = createUrl(uriString)!!
    return getResponseFromHttpUrl(requestUrl)
}

private fun uriBuilder(): String {
    val baseUri: Uri = Uri.parse(SCHEME_AUTHORITY)
    val uriBuilder: Uri.Builder = baseUri.buildUpon()
    uriBuilder.appendPath(APPEND_PATH)
    uriBuilder.appendPath(APPEND_ENDPOINT_POSTS)
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
