package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.domain.models.Industry

fun IndustriesDto.toIndustries(): Industry {
    return Industry(id = id, name = name)
}
