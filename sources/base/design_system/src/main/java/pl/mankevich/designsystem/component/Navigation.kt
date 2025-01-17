package pl.mankevich.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.isLandscape

@Composable
fun RnmNavigationSuiteScaffold(
    navigationSuiteItems: RnmNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedIconColor = RnmNavigationDefaults.navigationContentColor(),
            selectedTextColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedTextColor = RnmNavigationDefaults.navigationContentColor(),
            indicatorColor = RnmNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedIconColor = RnmNavigationDefaults.navigationContentColor(),
            selectedTextColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedTextColor = RnmNavigationDefaults.navigationContentColor(),
            indicatorColor = RnmNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedIconColor = RnmNavigationDefaults.navigationContentColor(),
            selectedTextColor = RnmNavigationDefaults.navigationSelectedContentColor(),
            unselectedTextColor = RnmNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            RnmNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = if (isLandscape()) NavigationSuiteType.NavigationRail else NavigationSuiteType.NavigationBar,
        containerColor = MaterialTheme.colorScheme.background,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.surface,
            navigationRailContainerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

/**
 * A wrapper around [NavigationSuiteScope] to declare navigation items.
 */
class RnmNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {

    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        onRepeatClick: (() -> Unit)? = null,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) {
        return navigationSuiteScope.item(
            icon = if (selected) selectedIcon else icon,
            label = label,
            selected = selected,
            onClick = {
                if (!selected) {
                    onClick()
                } else {
                    onRepeatClick?.invoke()
                }
            },
            modifier = modifier,
            colors = navigationSuiteItemColors,
        )
    }
}

@ThemePreviews
@Composable
fun RnmNavigationSuiteScaffoldPreview() {
    RnmTheme {
        RnmNavigationSuiteScaffold(
            navigationSuiteItems = {
                item(
                    selected = true,
                    icon = {
                        Icon(
                            imageVector = RnmIcons.Person,
                            contentDescription = "Characters",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = RnmIcons.PersonFilled,
                            contentDescription = "Characters",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = { Text("Characters") },
                    onClick = {},
                )
                item(
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = RnmIcons.MapPin,
                            contentDescription = "Locations",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = { Text("Locations") },
                    onClick = {},
                )
                item(
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = RnmIcons.MonitorPlay,
                            contentDescription = "Episodes",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = { Text("Episodes") },
                    onClick = {},
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
        }
    }
}

/**
 * RnM navigation default values.
 */
object RnmNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun navigationSelectedContentColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primary.copy(0.4f)
}
