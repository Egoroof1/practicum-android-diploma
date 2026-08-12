package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.VacancyRepositoryImpl
import ru.practicum.android.diploma.data.network.VacancyApiRepositoryImpl
import ru.practicum.android.diploma.domain.db.VacancyRepository
import ru.practicum.android.diploma.domain.network.VacancyApiRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), get())
    }

    single<VacancyApiRepository> {
        VacancyApiRepositoryImpl(get(), get())
    }
}
