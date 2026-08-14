package ru.practicum.android.diploma.presentation.home

import ru.practicum.android.diploma.domain.models.VacancyShort

private const val MOSCOW = "Москва"

const val DEMO_VACANCY_COUNT = 286

val demoVacancies = listOf(
    VacancyShort(
        id = "1",
        name = "Android-разработчик",
        city = MOSCOW,
        company = "Еда",
        salaryFrom = 100_000,
        salaryTo = null,
        salaryCurrency = "₽",
        logo = null,
    ),
    VacancyShort(
        id = "2",
        name = "Разработчик платформы данных",
        city = "Санкт-Петербург",
        company = "Алиса",
        salaryFrom = 1_500,
        salaryTo = null,
        salaryCurrency = "$",
        logo = null,
    ),
    VacancyShort(
        id = "3",
        name = "Java-разработчик",
        city = "Омск",
        company = "Маркет",
        salaryFrom = null,
        salaryTo = null,
        salaryCurrency = null,
        logo = null,
    ),
    VacancyShort(
        id = "4",
        name = "Разработчик на C++ в команду внутренних сервисов",
        city = MOSCOW,
        company = "Авто.ру",
        salaryFrom = 1_000,
        salaryTo = 1_500,
        salaryCurrency = "€",
        logo = null,
    ),
    VacancyShort(
        id = "5",
        name = "Разработчик бэкенда",
        city = MOSCOW,
        company = "Календарь",
        salaryFrom = 200_000,
        salaryTo = null,
        salaryCurrency = "₽",
        logo = null,
    ),
)
