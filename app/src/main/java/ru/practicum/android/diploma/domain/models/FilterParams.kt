package ru.practicum.android.diploma.domain.models

data class FilterParams(
    val industry: Industry?,
    val onlyWithSalary: Boolean,
    val minSalary: Int
)

data class Industry(
    val id: Int,
    val name: String?
)
