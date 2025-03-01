package pl.mankevich.coreui.navigation.typesafe

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry
import pl.mankevich.coreui.navigation.FeatureEntries
import kotlin.reflect.KClass

abstract class TypesafeComposableFeatureEntry<T : Any> : AnimatedFeatureEntry<KClass<T>>() {

    final override val arguments: List<NamedNavArgument> = super.arguments

    override fun NavGraphBuilder.composable(
        navController: NavHostController,
        featureEntries: FeatureEntries,
        useDeepLink: Boolean
    ) {
        composable(
            route = featureRoute,
            deepLinks = if (useDeepLink) deepLinks else emptyList(),
            enterTransition = {
                slideInHorizontally { fullWidth -> fullWidth } + fadeIn()
            },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = {
                slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
            }
        ) { backStackEntry ->
            Composable(navController, featureEntries, backStackEntry)
        }
    }
}
