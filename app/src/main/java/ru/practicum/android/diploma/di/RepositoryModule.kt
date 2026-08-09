package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.db.domain.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> { VacancyRepositoryImpl(get(), get()) }
}
