package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.data.network.VacancyApiRepositoryImpl
import ru.practicum.android.diploma.domain.db.VacancyDbRepository
import ru.practicum.android.diploma.domain.network.VacancyApiRepository

val repositoryModule = module {

    single<VacancyDbRepository> {
        VacancyDbRepositoryImpl(get(), get())
    }

    single<VacancyApiRepository> {
        VacancyApiRepositoryImpl(get(), get())
    }
}
