package pl.mankevich.coreui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pl.mankevich.designsystem.utils.ProvideAnimatedVisibilityScope

abstract class AnimatedFeatureEntry : ComposableFeatureEntry {

    @Composable
    override fun AnimatedContentScope.Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    ) {
        ProvideAnimatedVisibilityScope {
            AnimatedComposable(navController, featureEntries, backStackEntry)
        }
    }

    @Composable
    abstract fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    )
}
