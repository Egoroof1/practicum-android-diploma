package ru.practicum.android.diploma.domain.models

data class Developer(
    val name: String,
    val role: String,
    val iconRes: Int? = null,
    val imageRes: Int? = null,
    val github: String?,
)
