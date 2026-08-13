package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.mapper.VacancyApiConverter
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource

class VacancyApiRepositoryImpl(
    private val vacancyApi: VacancyApi,
    private val converter: VacancyApiConverter
) : VacancyApiRepository {

    override suspend fun searchAllVacancies(
        text: String?,
        page: Int?
    ): Resource<List<VacancyShort>> {
        return try {
            val response = vacancyApi.searchAllVacancies(
                text, page
            )

            if (response.items.isNotEmpty()) {
                Resource.Success(
                    data = response.items.map { converter.mapToVacancyShort(it) },
                    totalFound = response.found,
                    totalPages = response.pages,
                    currentPage = response.page
                )
            } else {
                Resource.Empty()
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ошибка при поиске вакансий")
        }
    }

    override suspend fun getVacancyById(vacancyId: String): Resource<VacancyFull> {
        return try {
            val response = vacancyApi.getVacancyById(vacancyId)
            Resource.Success(
                data = converter.mapToVacancyFull(response)
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ошибка при загрузке вакансии")
        }
    }
}
