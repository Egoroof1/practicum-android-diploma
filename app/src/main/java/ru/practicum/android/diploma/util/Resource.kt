package ru.practicum.android.diploma.util

sealed class Resource<out T> {
    data class Success<T>(
        val data: T,
        val totalFound: Int = 0,
        val totalPages: Int = 0,
        val currentPage: Int = 0
    ) : Resource<T>()

    data class Error(val message: String) : Resource<Nothing>()
    data class Loading(val progress: Float = 0f) : Resource<Nothing>()
    data class Empty(val message: String? = null) : Resource<Nothing>()
}
