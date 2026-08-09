package ru.practicum.android.diploma.db.data.dto

data class VacancyShortDto(
    val id: String,
    val name: String,
    val city: String,
    val company: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val logo: String?
)
