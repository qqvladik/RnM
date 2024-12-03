package pl.mankevich.designsystem.theme

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat

val DarkColorScheme = darkColorScheme(
    primary = BlueA,             // Used for prominent buttons or highlights (e.g., "Saiba mais" buttons).
    onPrimary = White,           // Text on top of the primary color.
    secondary = Pear,            // Accent color, e.g., for secondary highlights.
    onSecondary = BlackA,        // Text on top of the secondary color.
    background = BlackB,         // Background of the app.
    onBackground = White,        // Text color on the background.
    surface = BlackA,            // Surface color for cards or panels.
    onSurface = White            // Text color on surfaces.
)

val LightColorScheme = lightColorScheme(
    primary = BlueA,             // Same as in the dark theme, for consistency.
    onPrimary = White,           // Text on top of the primary color.
    secondary = Pear,            // Accent color, for elements like icons or details.
    onSecondary = BlackA,        // Text on top of the secondary color.
    background = White,          // Main background for the app.
    onBackground = DarkGray,     // Text color on the background.
    surface = LightGray,         // Surface color for cards or panels.
    onSurface = DarkGray         // Text color on surfaces.
)

@Composable
fun RnmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RnmTypography,
        content = content
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true, backgroundColor = 0xFF1E1E20)
annotation class ThemePreviews