package ru.practicum.android.diploma.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.ui.theme.Gray

@Composable
fun IndustryRow(
    industryName: String?,
    onClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ListItemHeight)
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.Spacing16),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (industryName == null) {
            Text(
                text = stringResource(id = R.string.filter_industry),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(Dimens.IconSize),
            )
        } else {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.filter_industry),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = industryName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            ClearIcon(onClick = onClearClick)
        }
    }
}

@Composable
fun SalaryField(
    salary: String,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val labelColor = if (isFocused || salary.isNotEmpty()) MaterialTheme.colorScheme.primary else Gray

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Spacing16)
            .clip(RoundedCornerShape(Dimens.CornerRadius))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = Dimens.Spacing16, vertical = Dimens.Spacing8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.filter_salary_label),
                style = MaterialTheme.typography.labelMedium,
                color = labelColor,
            )
            SalaryInput(
                salary = salary,
                interactionSource = interactionSource,
                viewModel = viewModel
            )
        }
        if (salary.isNotEmpty()) {
            ClearIcon(onClick = { viewModel.onSalaryChange("") })
        }
    }
}

@Composable
private fun SalaryInput(
    salary: String,
    interactionSource: MutableInteractionSource,
    viewModel: FilterViewModel
) {
    BasicTextField(
        value = salary,
        onValueChange = { newValue ->
            if (newValue.all { symbol -> symbol.isDigit() }) {
                viewModel.onSalaryChange(newValue)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Box {
                if (salary.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.filter_salary_hint),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Gray,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
fun OnlyWithSalaryRow(
    checked: Boolean,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ListItemHeight)
            .clickable { viewModel.isOnlySalaryChanged(!checked) }
            .padding(start = Dimens.Spacing16, end = Dimens.Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.filter_only_with_salary),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { newValue ->
                viewModel.isOnlySalaryChanged(newValue)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary,
            ),
        )
    }
}

@Composable
fun FilterButtons(
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Spacing16, vertical = Dimens.Spacing8),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing8),
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.filter_apply),
            onClick = onApplyClick,
        )
        TextButton(
            onClick = onResetClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight),
            shape = RoundedCornerShape(Dimens.CornerRadius),
        ) {
            Text(
                text = stringResource(id = R.string.filter_reset),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.ButtonHeight),
        shape = RoundedCornerShape(Dimens.CornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun ClearIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_close),
        contentDescription = stringResource(id = R.string.description_clear_field),
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .size(Dimens.IconSize)
            .clickable(onClick = onClick),
    )
}
