package br.com.fiap.inovagab.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightSurface,
    onSecondary = LightSurface,
    onTertiary = LightSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = ErrorColor
)

private val DarkColorScheme = darkColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = TextPrimary,
    surface = LightPrimary,
    onPrimary = LightSurface,
    onSecondary = LightSurface,
    onTertiary = LightSurface,
    onBackground = LightSurface,
    onSurface = LightSurface,
    error = ErrorColor
)

@Composable
fun InovaGABTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}