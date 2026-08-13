package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.AppDatabase
import ru.practicum.android.diploma.db.data.converters.VacancyDbConverter

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "vacancy_database.db"
        ).build()
    }

    single { VacancyDbConverter() }
}
