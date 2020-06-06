package com.developersbreach.developersbreach.repository.network

import android.net.Uri
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.utils.*
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*



fun getArticles(): NetworkArticlesContainer {
    val articlesNetworkList: List<ArticlesNetwork> = fetchArticleJsonData(articleResponse())
    return NetworkArticlesContainer(articlesNetworkList)
}

fun getTagsForArticle(links: String): List<Tags> {
    return fetchTagsJsonData(tagsResponse(links))
}

fun getAuthorFromNetwork(): NetworkAuthorContainer {
    val authorNetwork: AuthorNetwork = fetchAuthorJsonData(authorResponse())
    return NetworkAuthorContainer(authorNetwork)
}


@Throws(IOException::class)
private fun articleResponse(): String {
    val uriString: String = articleBuilder()
    val requestUrl: URL = createUrl(uriString)!!
    return getResponseFromHttpUrl(requestUrl)
}

@Throws(IOException::class)
fun tagsResponse(links: String): String {
    val uriString: String = links
    val requestUrl: URL = createUrl(uriString)!!
    return getResponseFromHttpUrl(requestUrl)
}

@Throws(IOException::class)
private fun authorResponse(): String {
    val uriString: String = authorBuilder()
    val requestUrl: URL = createUrl(uriString)!!
    return getResponseFromHttpUrl(requestUrl)
}


private fun articleBuilder(): String {
    val baseUri: Uri = Uri.parse(SCHEME_AUTHORITY)
    val uriBuilder: Uri.Builder = baseUri.buildUpon()
    uriBuilder.appendPath(APPEND_PATH)
    uriBuilder.appendPath(APPEND_ENDPOINT_POSTS)
    return uriBuilder.build().toString()
}

private fun authorBuilder(): String {
    val baseUri: Uri = Uri.parse(SCHEME_AUTHORITY)
    val uriBuilder: Uri.Builder = baseUri.buildUpon()
    uriBuilder.appendPath(APPEND_PATH)
    uriBuilder.appendPath(APPEND_ENDPOINT_USERS)
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
