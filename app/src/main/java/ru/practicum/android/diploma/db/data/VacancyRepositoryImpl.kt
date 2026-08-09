package ru.practicum.android.diploma.db.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.db.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.db.domain.VacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.models.VacancyFull

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
            .map { listDto ->
                listDto.map { dto ->
                    VacancyShort(
                        id = dto.id,
                        name = dto.name,
                        city = dto.city,
                        company = dto.company,
                        salaryFrom = dto.salaryFrom,
                        salaryTo = dto.salaryTo,
                        salaryCurrency = dto.salaryCurrency,
                        logo = dto.logo
                    )
                }
            }
}
