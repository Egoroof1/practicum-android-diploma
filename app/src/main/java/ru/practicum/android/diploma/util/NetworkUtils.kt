package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkUtils(
    context: Context
) {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.value = true
        }

        override fun onLost(network: Network) {
            _isConnected.value = false
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            _isConnected.value = networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            )
        }
    }
    init {
        registerNetworkCallback()
        _isConnected.value = isNetworkAvailable()
    }

    fun isNetworkAvailable(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                connectivityManager.activeNetwork
                    ?.let { connectivityManager.getNetworkCapabilities(it) }
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            }
            else -> {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }
    }

    private fun registerNetworkCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    fun unregisterNetworkCallback() {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}
