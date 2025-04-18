package pl.mankevich.dependencies

import androidx.compose.runtime.staticCompositionLocalOf
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.coreui.di.NavigationProvider
import pl.mankevich.domainapi.di.DomainProvider

interface DependenciesProvider :
    AndroidDependenciesProvider,
    NavigationProvider,
    DomainProvider

val LocalDependenciesProvider = staticCompositionLocalOf<DependenciesProvider> {
    error("No dependencies provider found!")
}