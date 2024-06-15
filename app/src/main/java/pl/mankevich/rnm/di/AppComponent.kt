package pl.mankevich.rnm.di

import android.app.Application
import android.content.Context
import dagger.Component
import pl.mankevich.core.di.providers.AndroidDependenciesProvider
import pl.mankevich.core.di.providers.DependenciesProvider
import pl.mankevich.core.di.providers.NetworkProvider
import pl.mankevich.core.di.providers.StorageProvider
import pl.mankevich.coreimpl.di.AndroidDependenciesComponent
import pl.mankevich.network.di.NetworkComponent
import pl.mankevich.storage.di.StorageComponent

@Component(
    dependencies = [
        AndroidDependenciesProvider::class,
        NetworkProvider::class,
        StorageProvider::class
    ]
)
interface AppComponent : DependenciesProvider {

    companion object {

        fun init(
            context: Context
        ): AppComponent { //TODO узнать почему здесь не DependenciesProvider
            val androidDependenciesProvider = AndroidDependenciesComponent.init(context)
            val networkProvider = NetworkComponent.init(androidDependenciesProvider)
            val storageProvider = StorageComponent.init(androidDependenciesProvider)
            return DaggerAppComponent.factory()
                .create(
                    androidDependenciesProvider,
                    networkProvider,
                    storageProvider
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            androidDependenciesProvider: AndroidDependenciesProvider,
            networkProvider: NetworkProvider,
            storageProvider: StorageProvider
        ): AppComponent
    }

    fun inject(app: Application)
}