package ru.practicum.android.diploma.util

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyFull

fun shareVacancy(context: Context, vacancy: VacancyFull?, salary: String) {
    if (vacancy == null) return

    val shareText = buildVacancyShareText(vacancy, salary)
    sendShareIntent(context, shareText, vacancy.vacancyName)
}

private fun buildVacancyShareText(vacancy: VacancyFull, salary: String): String {
    return buildString {
        appendBasicInfo(vacancy, salary)
        appendSkills(vacancy.skills)
    }.trimEnd()
}

private fun StringBuilder.appendBasicInfo(vacancy: VacancyFull, salary: String) {
    appendLine(vacancy.vacancyName)
    appendLine(vacancy.company ?: "")
    appendLine(vacancy.address ?: vacancy.city ?: "")
    appendLine(salary)
    appendLine("Опыт: ${vacancy.experience ?: "Не указан"}")
    appendLine("График: ${vacancy.schedule ?: "Не указан"}")
}

private fun StringBuilder.appendSkills(skills: List<String>) {
    if (skills.isEmpty()) return

    appendLine()
    appendLine("Ключевые навыки:")
    skills.forEach { skill ->
        appendLine("• $skill")
    }
}

private fun sendShareIntent(context: Context, shareText: String, subject: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }

    val chooser = Intent.createChooser(intent, context.getString(R.string.shar_vacancy))
    context.startActivity(chooser)
}
