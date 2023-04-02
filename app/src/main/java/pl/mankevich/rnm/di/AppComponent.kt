package pl.mankevich.rnm.di

import android.app.Application
import android.content.Context
import dagger.Component
import pl.mankevich.core.di.providers.DependenciesProvider
import pl.mankevich.core.di.providers.AndroidDependenciesProvider
import pl.mankevich.core.di.providers.NetworkProvider
import pl.mankevich.coreimpl.di.AndroidDependenciesComponent
import pl.mankevich.network.di.NetworkComponent

@Component(
    dependencies = [
        AndroidDependenciesProvider::class,
        NetworkProvider::class
    ]
)
interface AppComponent : DependenciesProvider {

    companion object {

        fun init(
            context: Context
        ): AppComponent { //TODO узнать почему здесь не DependenciesProvider
            val androidDependenciesProvider = AndroidDependenciesComponent.init(context)
            val networkProvider = NetworkComponent.init(androidDependenciesProvider)
            return DaggerAppComponent.factory()
                .create(androidDependenciesProvider, networkProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            androidDependenciesProvider: AndroidDependenciesProvider,
            networkProvider: NetworkProvider
        ): AppComponent
    }

    fun inject(app: Application)
}