package pl.mankevich.dependencies

import androidx.compose.runtime.staticCompositionLocalOf
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.NavigationProvider
import pl.mankevich.networkapi.di.NetworkProvider
import pl.mankevich.storageapi.di.StorageProvider

interface DependenciesProvider :
    NetworkProvider,
    AndroidDependenciesProvider,
    StorageProvider,
    NavigationProvider

val LocalDependenciesProvider = staticCompositionLocalOf<DependenciesProvider> {
    error("No data provider found!")
}