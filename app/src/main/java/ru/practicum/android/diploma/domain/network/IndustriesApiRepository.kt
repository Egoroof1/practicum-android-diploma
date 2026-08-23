package ru.practicum.android.diploma.domain.network

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesApiRepository {
    suspend fun getIndustries(): Resource<List<Industry>>
}
