package ru.practicum.android.diploma.presentation.filter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FilterScreen(
    onBackClick: () -> Unit,
    onIndustryClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel
) {
    val stateFilter by viewModel.state.collectAsStateWithLifecycle()
    val minSalary = stateFilter.selectedSalary
    val isOnlyWithSalary = stateFilter.isOnlyWithSalary
    val industryName = stateFilter.selectedIndustry?.name
    val isFilterChanged = stateFilter.isFilterChanged

    var showDiscardDialog by rememberSaveable { mutableStateOf(false) }

    BackHandler(
        enabled = true,
        onBack = {
            if (stateFilter.isFilterChanged) {
                showDiscardDialog = true
            } else {
                onBackClick()
            }
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.title_filter_settings),
                onBackClick = {
                    if (stateFilter.isFilterChanged) {
                        showDiscardDialog = true
                    } else {
                        onBackClick()
                    }
                },
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
                onClearClick = { viewModel.clearIndustry() },
            )
            SalaryField(
                salary = minSalary,
                modifier = Modifier.padding(top = Dimens.Spacing24),
                viewModel = viewModel
            )
            OnlyWithSalaryRow(
                checked = isOnlyWithSalary,
                modifier = Modifier.padding(top = Dimens.Spacing24),
                viewModel
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isFilterChanged) {
                FilterButtons(
                    onApplyClick = onApplyClick,
                    onResetClick = {
                        viewModel.clearIndustry()
                        viewModel.onSalaryChange("")
                        viewModel.isOnlySalaryChanged(false)
                        viewModel.isFilterCleared()
                    },
                )
            }
        }
    }

    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = {
                Text(
                    text = "Сохранить изменения?",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = "Настройки фильтрации изменились.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDiscardDialog = false
                        viewModel.setFilter()
                        onBackClick()
                    }
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDiscardDialog = false
                        viewModel.resetToLastAppliedFilter()
                        onBackClick()
                    }
                ) {
                    Text("Нет")
                }
            }
        )
    }
}
