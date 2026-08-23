package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.dto.VacancyFullResponseDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

interface VacancyApi {
    @GET("vacancies")
    suspend fun searchAllVacancies(
        @Query("text") text: String? = null,
        @Query("industry") industryId: Int? = null,
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean? = null,
        @Query("page") page: Int? = null
    ): VacancySearchResponseDto

    @GET("vacancies/{vacancyId}")
    suspend fun getVacancyById(
        @Path("vacancyId") vacancyId: String
    ): VacancyFullResponseDto

    @GET("industries")
    suspend fun getIndustries(): List<IndustriesDto>
}
