package pl.mankevich.coreui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

typealias FeatureEntries = Map<Class<out FeatureEntry>, @JvmSuppressWildcards FeatureEntry>

interface FeatureEntry {

    val featureRoute: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = emptyList()
}

interface ComposableFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.composable(
        navController: NavHostController,
        featureEntries: FeatureEntries
    ) {
        composable(featureRoute, arguments, deepLinks) { backStackEntry ->
            Composable(navController, featureEntries, backStackEntry)
        }
    }

    @Composable
    fun AnimatedContentScope.Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    )
}

interface AggregateFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.navigation(navController: NavHostController, featureEntries: FeatureEntries)
}


inline fun <reified T : FeatureEntry> FeatureEntries.find(): T =
    findOrNull() ?: error("Unable to find '${T::class.java}' in feature entries map.")

inline fun <reified T : FeatureEntry> FeatureEntries.findOrNull(): T? =
    this[T::class.java] as? T
