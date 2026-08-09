package ru.practicum.android.diploma.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.dto.VacancyShortDto
import ru.practicum.android.diploma.db.data.entity.VacancyFullEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyFullEntity)

    @Query("DELETE FROM favorite_vacancy WHERE Id =:vacancyId")
    suspend fun removeVacancyById(vacancyId: String): Int

    @Query("SELECT * FROM favorite_vacancy WHERE  Id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String) : VacancyFullEntity

    @Query("SELECT id, name, city, salaryFrom, salaryTo, salaryCurrency, logo FROM favorite_vacancy")
    fun getVacanciesListFlow(): Flow<List<VacancyShortDto>>
}
