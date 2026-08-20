package ru.practicum.android.diploma.util

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NetworkManager {
    private var networkUtils: NetworkUtils? = null
    private var isInitialized = false

    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState.asStateFlow()

    fun init(context: Context) {
        if (!isInitialized) {
            networkUtils = NetworkUtils(context.applicationContext)
            isInitialized = true
            // Начинаем слушать изменения сети
            networkUtils?.startListening { isConnected ->
                _connectionState.value = isConnected
            }
        }
    }

    fun isConnected(): Boolean {
        return _connectionState.value
    }

    fun getConnectionFlow(): StateFlow<Boolean> {
        return connectionState
    }
}
