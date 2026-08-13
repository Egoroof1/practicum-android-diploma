package ru.practicum.android.diploma.data.entity

import androidx.room.ColumnInfo

data class VacancyShortEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "company")
    val company: String?,
    @ColumnInfo(name = "salaryFrom")
    val salaryFrom: Int?,
    @ColumnInfo(name = "salaryTo")
    val salaryTo: Int?,
    @ColumnInfo(name = "salaryCurrency")
    val salaryCurrency: String?,
    @ColumnInfo(name = "logo")
    val logo: String?
)
