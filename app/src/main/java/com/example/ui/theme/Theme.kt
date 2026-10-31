package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AHSGCosmicDarkColorScheme = darkColorScheme(
    primary = CosmicCyan,
    onPrimary = Color(0xFF00363D),
    primaryContainer = CosmicCyanDim,
    onPrimaryContainer = Color(0xFFE0F7FA),
    
    secondary = PulsarViolet,
    onSecondary = Color(0xFF280680),
    secondaryContainer = Color(0xFF381A75),
    onSecondaryContainer = Color(0xFFEDE7F6),
    
    tertiary = SolarAmberNorth,
    onTertiary = Color(0xFF452B00),
    tertiaryContainer = Color(0xFF664000),
    onTertiaryContainer = Color(0xFFFFF8E1),
    
    background = DeepObsidian,
    onBackground = StarlightWhite,
    
    surface = SpaceCharcoal,
    onSurface = StarlightWhite,
    surfaceVariant = SpaceGlassNavy,
    onSurfaceVariant = StarlightMuted,
    
    outline = SpaceGlassBorder,
    outlineVariant = Color(0xFF1E283D)
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AHSGCosmicDarkColorScheme,
        typography = Typography,
        content = content
    )
}
