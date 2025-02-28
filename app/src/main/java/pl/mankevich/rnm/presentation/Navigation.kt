package pl.mankevich.rnm.presentation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import pl.mankevich.coreui.navigation.FeatureEntries
import pl.mankevich.coreui.navigation.navigateToTopLevelDestination
import pl.mankevich.coreui.navigation.toComposableFeatureEntries
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.designsystem.component.RnmNavigationSuiteScaffold
import pl.mankevich.designsystem.utils.ProvideSharedTransitionScope

@Composable
fun Navigation(
    rootNavController: NavHostController = rememberNavController(),
) {
    val featureEntries = LocalDependenciesProvider.current.featureEntries

    NavHost(
        navController = rootNavController,
        startDestination = MainRoute,
        modifier = Modifier.fillMaxSize(),
    ) {
        mainNavHost(featureEntries)

        deeplinkGraph(
            featureEntries = featureEntries,
            navController = rootNavController
        )
    }
}

@Serializable
data object MainRoute

fun NavGraphBuilder.mainNavHost(
    featureEntries: FeatureEntries
) {
    composable(MainRoute::class) {
        val mainNavController = rememberNavController()

        val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        RnmNavigationSuiteScaffold(
            navigationSuiteItems = {
                TopLevelDestination.entries.forEach { topLevelDestination ->
                    item(
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(topLevelDestination.destination::class)
                        } == true,
                        icon = {
                            Icon(
                                imageVector = topLevelDestination.icon(),
                                contentDescription = topLevelDestination.titleText,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = topLevelDestination.selectedIcon(),
                                contentDescription = topLevelDestination.titleText,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = { Text(topLevelDestination.titleText) },
                        onClick = {
                            mainNavController.navigateToTopLevelDestination(topLevelDestination.destination)
                        },
                        onRepeatClick = {
                            //TODO navigate to startDestination of nested navGraph (clearing backstack or navigating to the root of the graph if it is not in the backstack)
                        }
                    )
                }
            },
        ) {
            NavHost(
                navController = mainNavController,
                startDestination = TopLevelDestination.CHARACTERS.destination,
                modifier = Modifier.fillMaxSize()
            ) {

                TopLevelDestination.entries.forEach { topLevelDestination ->
                    composable(
                        route = topLevelDestination.destination::class,
                    ) {
                        SharedTransitionLayout {
                            ProvideSharedTransitionScope {
                                val nestedNavController = rememberNavController()
                                NavHost(
                                    navController = nestedNavController,
                                    startDestination = topLevelDestination.startDestination,
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    tabGraph(
                                        featureEntries = featureEntries,
                                        navController = nestedNavController
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.tabGraph(
    featureEntries: FeatureEntries,
    navController: NavHostController
) {
    allComposableGraph(featureEntries, navController, false)
}

fun NavGraphBuilder.deeplinkGraph(
    featureEntries: FeatureEntries,
    navController: NavHostController
) {
    allComposableGraph(featureEntries, navController, true)
}

fun NavGraphBuilder.allComposableGraph(
    featureEntries: FeatureEntries,
    navController: NavHostController,
    useDeeplink: Boolean
) {
    featureEntries.toComposableFeatureEntries().forEach { composableFeatureEntry ->
        with(composableFeatureEntry) {
            composable(navController, featureEntries, useDeeplink)
        }
    }
}
