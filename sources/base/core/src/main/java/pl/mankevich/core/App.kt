package pl.mankevich.core

import pl.mankevich.core.di.providers.DependenciesProvider

interface App {
    fun getDependenciesProvider(): DependenciesProvider
    fun cleanComponent()
}
