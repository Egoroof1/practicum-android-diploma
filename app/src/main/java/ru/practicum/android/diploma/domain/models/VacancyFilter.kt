package ru.practicum.android.diploma.domain.models

data class VacancyFilter(
    val industry: Industry?,
    val onlyWithSalary: Boolean?,
    val minSalary: String?
)

data class Industry(
    val id: String,
    val name: String
)
