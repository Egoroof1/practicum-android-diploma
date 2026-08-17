package ru.practicum.android.diploma.util

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.domain.models.VacancyFull

fun shareVacancy(context: Context, vacancy: VacancyFull?, salary: String) {
    if (vacancy == null) return
    val shareText = buildString {
        append("${vacancy.vacancyName}\n")
        append("${vacancy.company ?: ""}\n")
        append("${vacancy.address ?: vacancy.city ?: ""}\n")
        append("$salary\n")
        append("Опыт: ${vacancy.experience ?: "Не указан"}\n")
        append("График: ${vacancy.schedule ?: "Не указан"}\n")
        if (vacancy.skills.isNotEmpty()) {
            append("\nКлючевые навыки:\n")
            vacancy.skills.forEach { skill ->
                append("• $skill\n")
            }
        }
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    val chooser = Intent.createChooser(intent, "Поделиться вакансией")
    context.startActivity(chooser)
}
