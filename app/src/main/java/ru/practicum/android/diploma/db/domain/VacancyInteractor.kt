package ru.practicum.android.diploma.db.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.models.VacancyFull

interface VacancyInteractor {
    suspend fun insertVacancy(vacancy: VacancyFull)
    suspend fun removeVacancyById(vacancyId: String)
    suspend fun getVacancyById(vacancyId: String): VacancyFull
    fun getVacanciesList(): Flow<List<VacancyShort>>
}
