package ru.practicum.android.diploma.domain.network.usecase

import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource

class GetVacancyByIdUseCaseImpl(
    private val repository: VacancyApiRepository
) : GetVacancyByIdUseCase {
    override suspend operator fun invoke(vacancyId: String): Resource<VacancyFull> {
        return repository.getVacancyById(vacancyId)
    }
}

interface GetVacancyByIdUseCase {
    suspend operator fun invoke(vacancyId: String): Resource<VacancyFull>
}
