package ru.practicum.android.diploma.presentation.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Developer

@Composable
fun DeveloperBottomSheetContent(
    developer: Developer,
    onGithubClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (developer.imageRes != null) {
            Image(
                painterResource(developer.imageRes),
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape),
                contentDescription = developer.name,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = developer.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = developer.role, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        developer.github?.let {
            AssistChip(
                onClick = { onGithubClick(it) },
                label = { Text("GitHub") }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
