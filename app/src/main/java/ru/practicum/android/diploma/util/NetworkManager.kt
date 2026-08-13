package ru.practicum.android.diploma.util

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

object NetworkManager {
    private var networkUtils: NetworkUtils? = null
    private var isInitialized = false

    fun init(context: Context) {
        if (!isInitialized) {
            networkUtils = NetworkUtils(context.applicationContext)
            isInitialized = true
        }
    }

    fun isConnected(): Boolean {
        return networkUtils?.isConnected?.value ?: false
    }

    fun getConnectionFlow(): StateFlow<Boolean> {
        return networkUtils?.isConnected ?: error("NetworkManager не инициализирован")
    }
}
