package pl.mankevich.storage.di

import dagger.Component
import pl.mankevich.core.di.providers.AndroidDependenciesProvider
import pl.mankevich.core.di.providers.StorageProvider

@StorageScope
@Component(
    dependencies = [AndroidDependenciesProvider::class],
    modules = [
        DatabaseModule::class,
        DataSourceModule::class,
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
