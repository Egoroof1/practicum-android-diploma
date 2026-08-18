package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.entity.VacancyFullEntity
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.util.parseVacancyDescription
import kotlin.collections.emptyList

class VacancyDbConverter {
    fun mapToEntity(vacancy: VacancyFull): VacancyFullEntity {
        return VacancyFullEntity(
            id = vacancy.id,
            name = vacancy.vacancyName,
            city = vacancy.city,
            raw = vacancy.address,
            company = vacancy.company,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            logo = vacancy.logo,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            description = vacancy.description.description
        )
    }

    fun mapFromEntity(vacancy: VacancyFullEntity): VacancyFull {
        return VacancyFull(
            id = vacancy.id,
            vacancyName = vacancy.name,
            city = vacancy.city,
            address = vacancy.raw,
            company = vacancy.company,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            logo = vacancy.logo,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            description = parseVacancyDescription(vacancy.description),
            name = "",
            email = "",
            phone = emptyMap(),
            skills = emptyList(),
        )
    }
}
