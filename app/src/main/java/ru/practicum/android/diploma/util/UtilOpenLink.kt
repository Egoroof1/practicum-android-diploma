package ru.practicum.android.diploma.presentation.team

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
