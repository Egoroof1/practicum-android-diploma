package ru.practicum.android.diploma.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
