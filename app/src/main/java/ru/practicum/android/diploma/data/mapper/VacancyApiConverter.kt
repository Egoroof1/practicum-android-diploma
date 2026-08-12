package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancyFullResponseDto
import ru.practicum.android.diploma.data.dto.VacancyShortDto
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort

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
            otherDetails = buildOtherDetails(dto)
        )
    }

    private fun buildOtherDetails(dto: VacancyFullResponseDto): String {
        val parts = mutableListOf<String>()

        dto.employment?.name?.let { parts.add("Занятость: $it") }
        dto.address?.raw?.let { parts.add("Адрес: $it") }
        dto.description?.let { parts.add("Описание: ${it.take(200)}...") }
        dto.skills?.take(3)?.let { skills ->
            parts.add("Навыки: ${skills.joinToString(", ")}")
        }
        dto.contacts?.email?.let { parts.add("Email: $it") }
        dto.contacts?.phones?.take(1)?.let { phones ->
            parts.add("Телефон: ${phones.first().formatted}")
        }

        return parts.joinToString("\n\n")
    }
}
