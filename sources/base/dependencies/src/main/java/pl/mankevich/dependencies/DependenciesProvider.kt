package pl.mankevich.dependencies

import androidx.compose.runtime.staticCompositionLocalOf
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.NavigationProvider
import pl.mankevich.dataapi.di.DataProvider

interface DependenciesProvider :
    AndroidDependenciesProvider,
    NavigationProvider,
    DataProvider

val LocalDependenciesProvider = staticCompositionLocalOf<DependenciesProvider> {
    error("No data provider found!")
}