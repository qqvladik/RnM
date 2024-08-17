package pl.mankevich.rnm.di

import android.app.Application
import android.content.Context
import dagger.Component
import pl.mankevich.core.di.AndroidDependenciesComponent
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.data.di.DataComponent
import pl.mankevich.dataapi.di.DataProvider
import pl.mankevich.dependencies.DependenciesProvider
import pl.mankevich.network.di.NetworkRetrofitComponent
import pl.mankevich.storage.di.StorageRoomComponent

@FeatureScope
@Component(
    dependencies = [
        AndroidDependenciesProvider::class,
        DataProvider::class
    ],
    modules = [NavigationModule::class]
)
interface AppComponent : DependenciesProvider {

    companion object {

        fun init(
            context: Context
        ): AppComponent {
            val androidDependenciesProvider = AndroidDependenciesComponent.init(context)
            val networkProvider = NetworkRetrofitComponent.init(androidDependenciesProvider)
            val storageProvider = StorageRoomComponent.init(androidDependenciesProvider)
            val dataProvider = DataComponent.init(storageProvider, networkProvider)
            return DaggerAppComponent.factory()
                .create(
                    androidDependenciesProvider,
                    dataProvider
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            androidDependenciesProvider: AndroidDependenciesProvider,
            dataProvider: DataProvider
        ): AppComponent
    }

    fun inject(app: Application)
}