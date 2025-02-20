package pl.mankevich.coreui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import pl.mankevich.designsystem.utils.ProvideAnimatedVisibilityScope

typealias FeatureEntries = Map<Class<out FeatureEntry<*>>, @JvmSuppressWildcards FeatureEntry<*>>

interface FeatureEntry<T : Any> {

    val featureRoute: T

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = emptyList()
}

abstract class ComposableFeatureEntry<T : Any> : FeatureEntry<T> {

    abstract fun NavGraphBuilder.composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        useDeepLink: Boolean = true
    )

    @Composable
    protected abstract fun AnimatedContentScope.Composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    )
}

abstract class AnimatedFeatureEntry<T : Any> : ComposableFeatureEntry<T>() {

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
    protected abstract fun AnimatedComposable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        backStackEntry: NavBackStackEntry
    )
}

interface AggregateFeatureEntry : FeatureEntry<String> {

    fun NavGraphBuilder.navigation(navController: NavHostController, featureEntries: FeatureEntries)
}

inline fun <reified T : FeatureEntry<*>> FeatureEntries.find(): T =
    findOrNull() ?: error("Unable to find '${T::class.java}' in feature entries map.")

inline fun <reified T : FeatureEntry<*>> FeatureEntries.findOrNull(): T? =
    this[T::class.java] as? T

fun FeatureEntries.toComposableFeatureEntries(): List<ComposableFeatureEntry<*>> =
    map {
        it.value as? ComposableFeatureEntry<*>
            ?: error("Feature entry '${it.key}' is not a ComposableFeatureEntry.")
    }
