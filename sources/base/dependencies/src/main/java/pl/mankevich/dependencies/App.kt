package pl.mankevich.dependencies

import android.app.Application

interface App {

    fun getDependenciesProvider(): DependenciesProvider
}

val Application.dependenciesProvider: DependenciesProvider
    get() = (this as App).getDependenciesProvider()