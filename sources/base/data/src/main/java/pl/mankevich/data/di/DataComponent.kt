package pl.mankevich.data.di

import dagger.Component
import pl.mankevich.core.di.DispatchersModule
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.dataapi.di.DataProvider
import pl.mankevich.remoteapi.di.NetworkProvider
import pl.mankevich.databaseapi.di.StorageProvider

@FeatureScope
@Component(
    dependencies = [
        StorageProvider::class,
        NetworkProvider::class
    ],
    modules = [
        DispatchersModule::class,
        DataModule::class
    ]
)
interface DataComponent : DataProvider {

    companion object {

        fun init(
            storageProvider: StorageProvider,
            networkProvider: NetworkProvider
        ): DataProvider {
            return DaggerDataComponent.factory()
                .create(storageProvider, networkProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            storageProvider: StorageProvider,
            networkProvider: NetworkProvider
        ): DataProvider
    }
}
