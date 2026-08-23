package ru.practicum.android.diploma.presentation.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FilterScreen(
    industryName: String?,
    salary: String,
    onlyWithSalary: Boolean,
    onBackClick: () -> Unit,
    onIndustryClick: () -> Unit,
    onIndustryClear: () -> Unit,
    onSalaryChange: (String) -> Unit,
    onOnlyWithSalaryChange: (Boolean) -> Unit,
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasAnyFilter = industryName != null || salary.isNotEmpty() || onlyWithSalary

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.title_filter_settings),
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            IndustryRow(
                industryName = industryName,
                onClick = onIndustryClick,
                onClearClick = onIndustryClear,
            )
            SalaryField(
                salary = salary,
                onSalaryChange = onSalaryChange,
                modifier = Modifier.padding(top = Dimens.Spacing24),
            )
            OnlyWithSalaryRow(
                checked = onlyWithSalary,
                onCheckedChange = onOnlyWithSalaryChange,
                modifier = Modifier.padding(top = Dimens.Spacing24),
            )
            Spacer(modifier = Modifier.weight(1f))
            if (hasAnyFilter) {
                FilterButtons(
                    onApplyClick = onApplyClick,
                    onResetClick = onResetClick,
                )
            }
        }
    }
}
