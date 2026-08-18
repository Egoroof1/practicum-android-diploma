package ru.practicum.android.diploma.domain.network.usecase

import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacanciesUseCaseImpl(
    private val repository: VacancyApiRepository
) : SearchVacanciesUseCase{
    override suspend operator fun invoke(
        text: String?,
        page: Int?
    ): Resource<List<VacancyShort>> {
        return repository.searchAllVacancies(text, page)
    }
}
interface SearchVacanciesUseCase{
    suspend operator fun invoke(
        text: String?,
        page: Int?
    ): Resource<List<VacancyShort>>
}
