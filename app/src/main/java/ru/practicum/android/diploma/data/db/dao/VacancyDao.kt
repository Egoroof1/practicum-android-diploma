package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancyDbShortDto
import ru.practicum.android.diploma.data.entity.VacancyFullEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyFullEntity)

    @Query("DELETE FROM favorite_vacancy WHERE Id =:vacancyId")
    suspend fun removeVacancyById(vacancyId: String): Int

    @Query("SELECT * FROM favorite_vacancy WHERE  Id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): VacancyFullEntity

    @Query("SELECT id FROM favorite_vacancy")
    suspend fun getVacanciesIdsList(): List<String>

    @Query("SELECT id, name, city, salaryFrom, salaryTo, salaryCurrency, logo FROM favorite_vacancy")
    fun getVacanciesListFlow(): Flow<List<VacancyDbShortDto>>
}
