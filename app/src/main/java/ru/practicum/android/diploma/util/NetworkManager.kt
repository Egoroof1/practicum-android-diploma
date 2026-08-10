package ru.practicum.android.diploma.util

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

object NetworkManager {
    private var networkUtils: NetworkUtils? = null

    fun init(context: Context) {
        if (networkUtils == null) {
            networkUtils = NetworkUtils(context.applicationContext)
        }
    }

    fun isConnected(): Boolean {
        return networkUtils?.isConnected?.value ?: false
    }

    fun getConnectionFlow(): StateFlow<Boolean> {
        return networkUtils?.isConnected ?: error("NetworkManager не инициализирован")
    }

    fun unregister() {
        networkUtils?.unregisterNetworkCallback()
        networkUtils = null
    }
}
