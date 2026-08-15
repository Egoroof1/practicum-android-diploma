package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.entity.VacancyFullEntity
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.util.parseVacancyDescription

class VacancyDbConverter {
    fun mapToEntity(vacancy: VacancyFull): VacancyFullEntity {
        return VacancyFullEntity(
            id = vacancy.id,
            name = vacancy.name,
            city = vacancy.city,
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
            name = vacancy.name,
            city = vacancy.city,
            company = vacancy.company,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            logo = vacancy.logo,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            description = parseVacancyDescription(vacancy.description)
        )
    }
}
