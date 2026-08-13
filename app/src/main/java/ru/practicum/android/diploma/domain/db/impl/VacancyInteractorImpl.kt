package ru.practicum.android.diploma.domain.db.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.db.VacancyInteractor
import ru.practicum.android.diploma.domain.db.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort

class VacancyInteractorImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyInteractor {
    override suspend fun insertVacancy(vacancy: VacancyFull) {
        vacancyRepository.insertVacancy(vacancy)
    }

    override suspend fun removeVacancyById(vacancyId: String) {
        vacancyRepository.removeVacancyById(vacancyId)
    }

    override suspend fun getVacancyById(vacancyId: String): VacancyFull {
        return vacancyRepository.getVacancyById(vacancyId)
    }

    override fun getVacanciesList(): Flow<List<VacancyShort>> {
        return vacancyRepository.getVacanciesList()
    }

}
