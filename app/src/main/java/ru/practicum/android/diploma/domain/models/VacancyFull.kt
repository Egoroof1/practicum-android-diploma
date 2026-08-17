package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.util.ParsedVacancyDescription

data class VacancyFull(
    val id: String,
    val vacancyName: String,
    val city: String?,
    val address: String?,
    val company: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val logo: String?,
    val name: String?,
    val email: String?,
    val phone: Map<String, String>,
    val experience: String?,
    val schedule: String?,
    val description: ParsedVacancyDescription,
    val skills: List<String>
)
