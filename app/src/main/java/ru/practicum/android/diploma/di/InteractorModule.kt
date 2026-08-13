package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.VacancyInteractor
import ru.practicum.android.diploma.domain.db.impl.VacancyInteractorImpl

val interactorModule = module {

    factory<VacancyInteractor> { VacancyInteractorImpl(get()) }
}
