package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancyFullResponseDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

interface VacancyApi {
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("area") area: Int = 1,
        @Query("industry") industry: Int? = null,
        @Query("text") text: String? = null,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 200
    ): VacancySearchResponseDto

    @GET("vacancies/{vacancyId}")
    suspend fun getVacancyById(
        @Path("vacancyId") vacancyId: String
    ): VacancyFullResponseDto
}
