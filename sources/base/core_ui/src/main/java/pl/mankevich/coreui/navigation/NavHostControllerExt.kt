package pl.mankevich.coreui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


/**
 * UI logic for navigating to a top level destination in the app. Top level destinations have
 * only one copy of the destination of the back stack, and save and restore state whenever you
 * navigate to and from it.
 *
 * @param destination: The destination the app needs to navigate to.
 */
fun NavHostController.navigateToTopLevelDestination(destination: String) {
    navigate(destination) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}