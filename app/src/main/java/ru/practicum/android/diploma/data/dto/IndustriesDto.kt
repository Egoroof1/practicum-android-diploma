package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class IndustriesDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)
