package pl.mankevich.rnm.di

import android.app.Application
import android.content.Context
import dagger.Component
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.AndroidDependenciesComponent
import pl.mankevich.dependencies.DependenciesProvider
import pl.mankevich.network.di.NetworkComponent
import pl.mankevich.networkapi.di.NetworkProvider
import pl.mankevich.storage.di.StorageComponent
import pl.mankevich.storageapi.di.StorageProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        AndroidDependenciesProvider::class,
        NetworkProvider::class,
        StorageProvider::class
    ],
    modules = [NavigationModule::class]
)
interface AppComponent : DependenciesProvider {

    companion object {

        fun init(
            context: Context
        ): AppComponent {
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