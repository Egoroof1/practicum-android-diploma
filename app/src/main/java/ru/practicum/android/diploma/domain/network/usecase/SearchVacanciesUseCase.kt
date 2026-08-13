package ru.practicum.android.diploma.domain.network.usecase

import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacanciesUseCase(
    private val repository: VacancyApiRepository
) {
    suspend operator fun invoke(
        text: String?,
        page: Int?
    ): Resource<List<VacancyShort>> {
        return repository.searchAllVacancies(text, page)
    }
}
