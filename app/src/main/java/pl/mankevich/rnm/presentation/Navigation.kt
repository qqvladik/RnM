package pl.mankevich.rnm.presentation

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
import pl.mankevich.coreui.navigation.navigateToTopLevelDestination
import pl.mankevich.coreui.navigation.toComposableFeatureEntries
import pl.mankevich.dependencies.LocalDependenciesProvider
import pl.mankevich.designsystem.component.RnmNavigationSuiteScaffold

@Composable
fun Navigation() {
    val rootNavController = rememberNavController()
    val featureEntries = LocalDependenciesProvider.current.featureEntries

    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    RnmNavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { topLevelDestination ->
                item(
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelDestination.destination::class) } == true,
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
                        rootNavController.navigateToTopLevelDestination(topLevelDestination.destination)
                    },
                )
            }
        },
    ) {
        NavHost(
            navController = rootNavController,
            startDestination = TopLevelDestination.CHARACTERS.destination,
            modifier = Modifier.fillMaxSize()
        ) {

            fun NavGraphBuilder.allComposable(navController: NavHostController = rootNavController) {
                featureEntries.toComposableFeatureEntries().forEach { composableFeatureEntry ->
                    with(composableFeatureEntry) {
                        composable(navController, featureEntries)
                    }
                }
            }

            TopLevelDestination.entries.forEach { topLevelDestination ->
                composable(
                    route = topLevelDestination.destination::class,
                ) {
                    val nestedNavController = rememberNavController()
                    NavHost(
                        navController = nestedNavController,
                        startDestination = topLevelDestination.startDestination,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        allComposable(nestedNavController)
                    }
                }
            }
        }
    }
}
