package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort

interface VacancyDbInteractor {
    suspend fun insertVacancy(vacancy: VacancyFull)
    suspend fun removeVacancyById(vacancyId: String)
    suspend fun getVacancyById(vacancyId: String): VacancyFull
    suspend fun getVacanciesIdsList(): List<String>
    fun getVacanciesList(): Flow<List<VacancyShort>>
}
