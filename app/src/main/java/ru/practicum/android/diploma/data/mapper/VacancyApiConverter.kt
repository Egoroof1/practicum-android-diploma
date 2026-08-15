package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyFullResponseDto
import ru.practicum.android.diploma.data.dto.VacancyShortDto
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.util.parseVacancyDescription

class VacancyApiConverter {

    fun mapToVacancyShort(dto: VacancyShortDto): VacancyShort {
        return VacancyShort(
            id = dto.id,
            name = dto.name,
            city = dto.city ?: "Не указан",
            company = dto.company,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            salaryCurrency = dto.salary?.currency,
            logo = dto.logo
        )
    }

    fun mapToVacancyFull(dto: VacancyFullResponseDto): VacancyFull {
        return VacancyFull(
            id = dto.id,
            name = dto.name,
            city = dto.address?.city ?: dto.area?.name ?: "Не указан",
            company = dto.employer?.name,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            salaryCurrency = dto.salary?.currency,
            logo = dto.employer?.logo,
            experience = dto.experience?.name,
            schedule = dto.schedule?.name,
            description = parseVacancyDescription(dto.description),
            skills = dto.skills ?: emptyList()
        )
    }
}
