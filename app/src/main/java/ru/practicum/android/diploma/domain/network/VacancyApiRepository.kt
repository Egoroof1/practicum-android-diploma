package ru.practicum.android.diploma.domain.network

import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.util.Resource

interface VacancyApiRepository {
    suspend fun searchVacancies(
        text: String? = null,
        industry: Int? = null,
        page: Int = 0,
        perPage: Int = 20
    ): Resource<List<VacancyShort>>

    suspend fun getVacancyById(vacancyId: String): Resource<VacancyFull>
}
