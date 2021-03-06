@file:Suppress("DEPRECATION")

package com.developersbreach.developersbreach.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun capitalizeWord(title: String): String? {
    val splitTitle =
        title.toLowerCase(Locale.getDefault()).split(" ".toRegex()).toTypedArray()
    val builder = StringBuilder()
    for (i in splitTitle.indices) {
        val currentWordInTitle = splitTitle[i]
        if (i > 0 && currentWordInTitle.isNotEmpty()) {
            builder.append(" ")
        }
        val capitalTitle = (currentWordInTitle.substring(0, 1).toUpperCase(Locale.getDefault())
                + currentWordInTitle.substring(1))
        builder.append(capitalTitle)
    }
    return builder.toString()
}


fun showSnackBar(message: String, activity: Activity) {
    val decorView = activity.window.decorView
    val view: View = decorView.findViewById(android.R.id.content)
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun isNetworkConnected(context: Context): Boolean {

    val manager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return when {

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {

            val activeNetwork: Network? = manager.activeNetwork
            val capabilities: NetworkCapabilities? =
                manager.getNetworkCapabilities(activeNetwork)
            capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        else -> {
            val networkInfo: NetworkInfo? = manager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}
