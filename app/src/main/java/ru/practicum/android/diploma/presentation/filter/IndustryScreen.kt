package ru.practicum.android.diploma.presentation.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.components.ScreenPlaceholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun IndustryScreen(
    industries: List<String>,
    selectedIndustry: String?,
    query: String,
    isLoading: Boolean,
    isError: Boolean,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onIndustryClick: (String) -> Unit,
    onChooseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visibleIndustries = industries.filter { name -> name.contains(query, ignoreCase = true) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.title_industry_choose),
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            SearchField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { },
                modifier = Modifier.padding(horizontal = Dimens.Spacing16, vertical = Dimens.Spacing8),
                placeholderText = stringResource(id = R.string.hint_enter_industry),
            )
            IndustryContent(
                industries = visibleIndustries,
                selectedIndustry = selectedIndustry,
                isLoading = isLoading,
                isError = isError,
                onIndustryClick = onIndustryClick,
                modifier = Modifier.weight(1f),
            )
            if (selectedIndustry != null) {
                PrimaryButton(
                    text = stringResource(id = R.string.filter_choose),
                    onClick = onChooseClick,
                    modifier = Modifier.padding(
                        horizontal = Dimens.Spacing16,
                        vertical = Dimens.Spacing8,
                    ),
                )
            }
        }
    }
}

@Composable
private fun IndustryContent(
    industries: List<String>,
    selectedIndustry: String?,
    isLoading: Boolean,
    isError: Boolean,
    onIndustryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(Dimens.ProgressSize),
                color = MaterialTheme.colorScheme.primary,
            )

            isError -> ScreenPlaceholder(
                imageRes = R.drawable.il_nothing_found,
                text = stringResource(id = R.string.placeholder_industry_failed),
            )

            industries.isEmpty() -> ScreenPlaceholder(
                imageRes = R.drawable.il_nothing_found,
                text = stringResource(id = R.string.placeholder_industry_not_found),
            )

            else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = industries) { industry ->
                    IndustryItem(
                        name = industry,
                        isSelected = industry == selectedIndustry,
                        onClick = { onIndustryClick(industry) },
                    )
                }
            }
        }
    }
}

@Composable
private fun IndustryItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.ListItemHeight)
            .clickable(onClick = onClick)
            .padding(start = Dimens.Spacing16, end = Dimens.Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}
