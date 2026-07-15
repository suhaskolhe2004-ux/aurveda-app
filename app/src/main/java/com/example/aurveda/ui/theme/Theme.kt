package com.example.aurveda.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = LightAccentTeal,
    onPrimary = LightBackground,
    secondary = LightHighlightCoral,
    onSecondary = LightBackground,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightCardBackground,
    onSurface = LightTextPrimary,
    surfaceVariant = LightCardBackground,
    onSurfaceVariant = LightTextSecondary,
    error = LightHighlightCoral
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccentTeal,
    onPrimary = DarkBackground,
    secondary = DarkHighlightCoral,
    onSecondary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkCardBackground,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkCardBackground,
    onSurfaceVariant = DarkTextSecondary,
    error = DarkHighlightCoral
)

@Composable
fun AurvedaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}