package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyItem(
    vacancy: VacancyShort,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.Spacing16, vertical = Dimens.Spacing9),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing12),
    ) {
        CompanyLogo(logoUrl = vacancy.logo)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.vacancy_title, vacancy.name, vacancy.city),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = vacancy.company ?: stringResource(id = R.string.company_not_specified),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = salaryText(
                    from = vacancy.salaryFrom,
                    to = vacancy.salaryTo,
                    currency = vacancy.salaryCurrency,
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
