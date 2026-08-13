package ru.practicum.android.diploma.db.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.data.entity.VacancyFullEntity

@Database(
    version = 1,
    entities = [
        VacancyFullEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
