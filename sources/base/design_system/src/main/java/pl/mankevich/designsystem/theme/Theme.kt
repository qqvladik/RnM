package pl.mankevich.designsystem.theme

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import pl.mankevich.designsystem.utils.ProvideAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.ProvideSharedTransitionScope

val DarkColorScheme = darkColorScheme(
    primary = BlueA,             // Used for prominent buttons or highlights (e.g., "Saiba mais" buttons).
    onPrimary = White,           // Text on top of the primary color.
    secondary = Pear,            // Accent color, e.g., for secondary highlights.
    onSecondary = BlackA,        // Text on top of the secondary color.
    background = BlackB,         // Background of the app.
    onBackground = White,        // Text color on the background.
    surface = BlackA,            // Surface color for cards or panels.
    onSurface = White,            // Text color on surfaces.,
    inversePrimary = BlueA,       // Text color for action buttons in snackbar.
    inverseSurface = LightGray,   // Color for contanier in snackbar.
    inverseOnSurface = DarkGray,  // Text color for content in snackbar.
    surfaceContainerHigh = BlackB,// Container color on pull to refresh Indicator
    onSurfaceVariant = BlueA,     // Indicator color on pull to refresh Indicator

)

val LightColorScheme = lightColorScheme(
    primary = BlueA,             // Same as in the dark theme, for consistency.
    onPrimary = White,           // Text on top of the primary color.
    secondary = Pear,            // Accent color, for elements like icons or details.
    onSecondary = BlackA,        // Text on top of the secondary color.
    background = White,          // Main background for the app.
    onBackground = DarkGray,     // Text color on the background.
    surface = LightGray,         // Surface color for cards or panels.
    onSurface = DarkGray,         // Text color on surfaces.
    inversePrimary = BlueA,       // Color for action buttons in snackbar. Same as in the dark theme, for consistency.
    inverseSurface = BlackB,      // Color for contanier in snackbar.
    inverseOnSurface = White,     // Text color for content in snackbar.
    surfaceContainerHigh = LightGray,// Container color on pull to refresh Indicator
    onSurfaceVariant = BlueA,     // Indicator color on pull to refresh Indicator
)

@Composable
fun RnmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RnmTypography,
    ) {
        SharedTransitionLayout {
            ProvideSharedTransitionScope {
                // As there is no NavHost used in Previews, we need to provide dummy AnimatedVisibilityScope ourselves
                if (LocalInspectionMode.current) {
                    AnimatedVisibility(
                        visible = true,
                        label = "LocalAnimatedVisibility"
                    ) {
                        ProvideAnimatedVisibilityScope(content)
                    }
                } else {
                    content()
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "1 Light theme portrait",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "1 Dark theme portrait",
    showBackground = true,
    backgroundColor = 0xFF1E1E20
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "2 Light theme landscape",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, device = "spec:parent=pixel_5,orientation=landscape"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "2 Dark theme landscape",
    showBackground = true,
    backgroundColor = 0xFF1E1E20, device = "spec:parent=pixel_5,orientation=landscape"
)
annotation class ThemePreviews