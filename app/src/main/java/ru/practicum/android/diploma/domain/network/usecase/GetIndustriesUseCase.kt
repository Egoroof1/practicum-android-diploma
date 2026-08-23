package ru.practicum.android.diploma.domain.network.usecase

import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.domain.network.IndustriesApiRepository
import ru.practicum.android.diploma.util.Resource

class GetIndustriesUseCaseImpl(
    private val repository: IndustriesApiRepository
) : GetIndustriesUseCase {
    override suspend fun invoke(): Resource<List<Industries>> {
        return repository.getIndustries()
    }
}

interface GetIndustriesUseCase {
    suspend operator fun invoke(): Resource<List<Industries>>
}
