package ru.practicum.android.diploma.domain.models

data class VacancyFull(
    val id: String,
    val name: String,
    val city: String?,
    val company: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val logo: String?,
// -- Продумать что с этим делать. когда будем организовывать поиск и общее отображение
    val experience: String?,
    val schedule: String?,
    val otherDetails: String?
// --
)
