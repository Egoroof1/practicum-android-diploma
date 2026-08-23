package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.mapper.toIndustries
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.domain.network.IndustriesApiRepository
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

class IndustriesRepositoryImpl(
    private val industriesApi: VacancyApi
) : IndustriesApiRepository {
    override suspend fun getIndustries(): Resource<List<Industries>> {
        return try {
            val response = industriesApi.getIndustries()
            val industries = response.map { dto -> dto.toIndustries() }
            Resource.Success(industries)
        } catch (e: IOException) {
            Resource.Error("Ошибка сети: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Ошибка загрузки: ${e.message}")
        }
    }
}
