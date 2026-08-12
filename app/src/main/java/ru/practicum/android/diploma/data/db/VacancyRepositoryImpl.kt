package ru.practicum.android.diploma.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.domain.db.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort

class VacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConverter: VacancyDbConverter
) : VacancyRepository {
    override suspend fun insertVacancy(vacancy: VacancyFull) {
        appDatabase.vacancyDao().insertVacancy(vacancyDbConverter.mapToEntity(vacancy))
    }

    override suspend fun removeVacancyById(vacancyId: String) {
        appDatabase.vacancyDao().removeVacancyById(vacancyId)
    }

    override suspend fun getVacancyById(vacancyId: String): VacancyFull {
        val vacancy = appDatabase.vacancyDao().getVacancyById(vacancyId)
        return vacancyDbConverter.mapFromEntity(vacancy)
    }

    override fun getVacanciesList(): Flow<List<VacancyShort>> =
        appDatabase.vacancyDao().getVacanciesListFlow()
            .map { listEntity ->
                listEntity.map { entity ->
                    VacancyShort(
                        id = entity.id,
                        name = entity.name,
                        city = entity.city,
                        company = entity.company,
                        salaryFrom = entity.salaryFrom,
                        salaryTo = entity.salaryTo,
                        salaryCurrency = entity.salaryCurrency,
                        logo = entity.logo
                    )
                }
            }
}
