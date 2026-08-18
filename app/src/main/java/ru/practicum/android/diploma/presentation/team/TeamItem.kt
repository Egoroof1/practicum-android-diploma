package ru.practicum.android.diploma.presentation.team

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Developer

@Composable
fun TeamItem(dev: Developer, onClick: () -> Unit) {
    val painter = dev.iconRes ?: R.drawable.ic_team_24px

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(painter),
                modifier = Modifier.size(48.dp),
                contentDescription = "",
                tint = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = dev.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = dev.role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(name = "Portrait")
@Composable
private fun ItemPreview() {
    TeamItem(
        Developer(
            name = "Иван Федорович Крузенштерн",
            role = "Человек и пароход",
            iconRes = R.drawable.ic_dev_48,
            github = null
        ),
        onClick = {}
    )
}
