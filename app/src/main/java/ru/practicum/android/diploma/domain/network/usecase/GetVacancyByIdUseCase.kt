package ru.practicum.android.diploma.domain.network.usecase

import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource

class GetVacancyByIdUseCase(
    private val repository: VacancyApiRepository
) {
    suspend operator fun invoke(vacancyId: String): Resource<VacancyFull> {
        return repository.getVacancyById(vacancyId)
    }
}
