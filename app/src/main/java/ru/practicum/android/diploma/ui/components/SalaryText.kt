package ru.practicum.android.diploma.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import java.util.Locale

@Composable
fun salaryText(from: Int?, to: Int?, currency: String?): String {
    if (from == null && to == null) {
        return stringResource(id = R.string.salary_not_specified)
    }
    val parts = listOfNotNull(
        from?.let { stringResource(id = R.string.salary_from, formatAmount(it)) },
        to?.let { stringResource(id = R.string.salary_to, formatAmount(it)) },
        currency,
    )
    return parts.joinToString(separator = " ")
}

private fun formatAmount(amount: Int): String =
    String.format(Locale.ROOT, "%,d", amount).replace(oldChar = ',', newChar = ' ')
