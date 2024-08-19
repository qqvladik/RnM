package pl.mankevich.data.di

import dagger.Component
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.dataapi.di.DataProvider
import pl.mankevich.networkapi.di.NetworkProvider
import pl.mankevich.storageapi.di.StorageProvider

@FeatureScope
@Component(
    dependencies = [
        StorageProvider::class,
        NetworkProvider::class
    ],
    modules = [
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