package pl.mankevich.remoteretrofit.di

import dagger.Component
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.remoteapi.di.NetworkProvider

@Component(
    dependencies = [
        AndroidDependenciesProvider::class
    ],
    modules = [
        HttpUrlModule::class,
        RetrofitModule::class,
        NetworkRetrofitApiModule::class
    ]
)
interface NetworkRetrofitComponent : NetworkProvider {

    companion object {

        fun init(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): NetworkProvider {
            return DaggerNetworkRetrofitComponent.factory()
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