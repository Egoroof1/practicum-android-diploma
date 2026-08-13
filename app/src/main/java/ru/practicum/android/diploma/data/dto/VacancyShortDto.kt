package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancySearchResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyShortDto>
)

data class VacancyShortDto(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    @SerializedName("salary")
    val salary: SalaryDto?,
    @SerializedName("logo")
    val logo: String?
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)
