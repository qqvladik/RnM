package pl.mankevich.storage.di

import dagger.Component
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.storageapi.di.StorageProvider

@FeatureScope
@Component(
    dependencies = [AndroidDependenciesProvider::class],
    modules = [
        DatabaseModule::class,
        StorageDataSourceModule::class,
    ]
)
interface StorageComponent : StorageProvider {

    companion object {

        fun init(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): StorageProvider {
            return DaggerStorageComponent.factory()
                .create(androidDependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): StorageProvider
    }
}
