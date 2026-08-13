package ru.practicum.android.diploma.presentation.home

data class DemoVacancy(
    val id: String,
    val title: String,
    val employer: String,
    val salary: String,
)

const val DEMO_VACANCY_COUNT = 286

val demoVacancies = listOf(
    DemoVacancy("1", "Android-разработчик, Москва", "Еда", "от 100 000 ₽"),
    DemoVacancy("2", "Разработчик платформы данных, Санкт-Петербург", "Алиса", "от 1 500 $"),
    DemoVacancy("3", "Java-разработчик, Омск", "Маркет", "Зарплата не указана"),
    DemoVacancy("4", "Разработчик на C++, Москва", "Авто.ру", "от 1 000 до 1 500 €"),
    DemoVacancy("5", "Разработчик бэкенда, Москва", "Календарь", "от 200 000 ₽"),
)
