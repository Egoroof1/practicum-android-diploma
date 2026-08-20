package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy")
data class VacancyFullEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val city: String?,
    val address: String?,
    val company: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val logo: String?,
    val contactName: String?,
    val contactEmail: String?,
    val phoneJson: String?,
    val experience: String?,
    val schedule: String?,
    val description: String?,
    val responsibilitiesJson: String?,
    val requirementsJson: String?,
    val conditionsJson: String?,
    val skillsJson: String?
)
