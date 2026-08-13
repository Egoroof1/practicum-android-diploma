package ru.practicum.android.diploma.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy")
data class VacancyFullEntity(
    @PrimaryKey
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
