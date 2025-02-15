package pl.mankevich.coreui.navigation.string

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import pl.mankevich.coreui.navigation.AnimatedFeatureEntry
import pl.mankevich.coreui.navigation.FeatureEntries

abstract class StringComposableFeatureEntry : AnimatedFeatureEntry<String>() {

    override fun NavGraphBuilder.composable(
        navController: NavHostController,
        featureEntries: FeatureEntries
    ) {
        composable(
            route = featureRoute,
            arguments = arguments,
            deepLinks = deepLinks
        ) { backStackEntry ->
            Composable(navController, featureEntries, backStackEntry)
        }
    }
}
