package com.developersbreach.developersbreach.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkManager {

    companion object {

        fun checkNetwork(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val networkInfo = connectivityManager.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            } else {
                var hasNetwork = true
                // Gets all available network types
                val networks = connectivityManager.allNetworks
                // If length is not zero we have connection types to perform capabilities.
                if (networks.isNotEmpty()) {
                    for (network in networks) {
                        // Representation of the capabilities of an active network.
                        val capabilities =
                            connectivityManager.getNetworkCapabilities(network)
                        // Returns false if no capabilities are known or un-available.
                        hasNetwork = capabilities != null &&
                                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    }
                }
                // Return boolean value, true if network available else false.
                hasNetwork
            }
        }
    }
}
