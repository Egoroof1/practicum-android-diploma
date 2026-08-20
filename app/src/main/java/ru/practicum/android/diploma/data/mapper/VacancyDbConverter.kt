package ru.practicum.android.diploma.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.entity.VacancyFullEntity
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.util.ParsedVacancyDescription

class VacancyDbConverter(private val gson: Gson) {
    fun mapToEntity(vacancy: VacancyFull): VacancyFullEntity {
        return VacancyFullEntity(
            id = vacancy.id,
            name = vacancy.vacancyName,
            city = vacancy.city,
            address = vacancy.address,
            company = vacancy.company,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            logo = vacancy.logo,
            contactName = vacancy.name,
            contactEmail = vacancy.email,
            phoneJson = phoneJsonFromMap(vacancy.phone),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            description = vacancy.description.description,
            responsibilitiesJson = jsonFromList(vacancy.description.responsibilities),
            requirementsJson = jsonFromList(vacancy.description.requirements),
            conditionsJson = jsonFromList(vacancy.description.conditions),
            skillsJson = jsonFromList(vacancy.skills)

        )
    }

    fun mapFromEntity(vacancy: VacancyFullEntity): VacancyFull {
        return VacancyFull(
            id = vacancy.id,
            vacancyName = vacancy.name,
            city = vacancy.city,
            address = vacancy.address,
            company = vacancy.company,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            logo = vacancy.logo,
            name = vacancy.contactName,
            email = vacancy.contactEmail,
            phone = phoneMapFromJson(vacancy.phoneJson),
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            description = ParsedVacancyDescription(
                description = vacancy.description,
                responsibilities = listFromJson(vacancy.responsibilitiesJson),
                requirements = listFromJson(vacancy.requirementsJson),
                conditions = listFromJson(vacancy.conditionsJson)
            ),
            skills = listFromJson(vacancy.skillsJson),
        )
    }

    private fun phoneJsonFromMap(map: Map<String, String>?): String? = map?.let { gson.toJson(it) }

    private fun phoneMapFromJson(json: String?): Map<String, String> {
        if (json.isNullOrBlank()) return emptyMap()
        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun jsonFromList(list: List<String>?): String? = list?.let { gson.toJson(it) }

    private fun listFromJson(json: String?): List<String> {
        if (json.isNullOrBlank()) return emptyList()
        return gson.fromJson(json, Array<String>::class.java).toList()
    }
}
