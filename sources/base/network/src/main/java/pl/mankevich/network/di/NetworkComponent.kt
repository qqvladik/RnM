package pl.mankevich.network.di

import dagger.Component
import pl.mankevich.core.di.providers.AndroidDependenciesProvider
import pl.mankevich.core.di.providers.NetworkProvider

@Component(
    dependencies = [
        AndroidDependenciesProvider::class
    ],
    modules = [
        HttpUrlModule::class,
        NetworkModule::class
    ]
)
interface NetworkComponent : NetworkProvider {

    companion object {

        fun init(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): NetworkProvider {
            return DaggerNetworkComponent.factory()
                .create(androidDependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): NetworkProvider
    }
}