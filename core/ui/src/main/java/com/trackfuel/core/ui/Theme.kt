package com.trackfuel.core.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Neon Green & Cyan dark theme accents
val NeonGreen = Color(0xFF00FF66)
val NeonCyan = Color(0xFF00E5FF)
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkSurfaceVariant = Color(0xFF2C2C2C)

val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF004D1A),
    onPrimaryContainer = NeonGreen,
    secondary = NeonCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004D5A),
    onSecondaryContainer = NeonCyan,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCCCCCC)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF008A38),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB3FFCA),
    onPrimaryContainer = Color(0xFF002108),
    secondary = Color(0xFF006874),
    onSecondary = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1E)
)
