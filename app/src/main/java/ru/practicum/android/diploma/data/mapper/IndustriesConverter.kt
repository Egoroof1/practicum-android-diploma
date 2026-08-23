package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.domain.models.Industries

fun IndustriesDto.toIndustries(): Industries {
    return Industries(id = this.id, name = this.name)
}
