package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

private const val LOGO_USER_AGENT = "Android Diploma App"

@Composable
fun CompanyLogo(
    logoUrl: String?,
    modifier: Modifier = Modifier,
) {
    var isLogoLoaded by remember(logoUrl) { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(Dimens.LogoSize)
            .clip(RoundedCornerShape(Dimens.CornerRadius)),
        contentAlignment = Alignment.Center,
    ) {
        if (!isLogoLoaded) {
            Icon(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(logoUrl)
                .setHeader("User-Agent", LOGO_USER_AGENT)
                .build(),
            contentDescription = stringResource(id = R.string.description_company_logo),
            contentScale = ContentScale.Fit,
            onState = { state -> isLogoLoaded = state is AsyncImagePainter.State.Success },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
