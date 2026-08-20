package ru.practicum.android.diploma.util

data class ParsedVacancyDescription(
    val description: String?,
    val responsibilities: List<String>,
    val requirements: List<String>,
    val conditions: List<String>
)

fun parseVacancyDescription(htmlDescription: String?): ParsedVacancyDescription {
    if (htmlDescription.isNullOrBlank()) {
        return ParsedVacancyDescription("", emptyList(), emptyList(), emptyList())
    }

    val cleanHtml = htmlDescription
        .replace("<[^>]*>".toRegex(), "\n")
        .replace("\n+".toRegex(), "\n")
        .trim()

    // Разбиваем на секции
    val sections = cleanHtml.split(Regex("Обязанности|Требования|Условия"))

    return ParsedVacancyDescription(
        description = sections.getOrNull(0)?.trim(),
        responsibilities = parseListItems(sections.getOrNull(ONE) ?: ""),
        requirements = parseListItems(sections.getOrNull(TWO) ?: ""),
        conditions = parseListItems(sections.getOrNull(THREE) ?: "")
    )
}

private fun parseListItems(text: String): List<String> {
    return text
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() && !it.matches(Regex("^\\d+$")) }
}

private const val ONE = 1
private const val TWO = 2
private const val THREE = 3
