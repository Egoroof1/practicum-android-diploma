package ru.practicum.android.diploma.domain.db.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.db.VacancyDbInteractor
import ru.practicum.android.diploma.domain.db.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort

class VacancyDbInteractorImpl(
    private val vacancyDbRepository: VacancyDbRepository
) : VacancyDbInteractor {
    override suspend fun insertVacancy(vacancy: VacancyFull) {
        vacancyDbRepository.insertVacancy(vacancy)
    }

    override suspend fun removeVacancyById(vacancyId: String) {
        vacancyDbRepository.removeVacancyById(vacancyId)
    }

    override suspend fun getVacancyById(vacancyId: String): VacancyFull {
        return vacancyDbRepository.getVacancyById(vacancyId)
    }

    override suspend fun getVacanciesIdsList(): List<String> {
        return vacancyDbRepository.getVacanciesIdsList()
    }

    override fun getVacanciesList(): Flow<List<VacancyShort>> {
        return vacancyDbRepository.getVacanciesList()
    }

}
