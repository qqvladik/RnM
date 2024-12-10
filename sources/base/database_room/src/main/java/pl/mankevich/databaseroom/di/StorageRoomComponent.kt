package pl.mankevich.databaseroom.di

import dagger.Component
import pl.mankevich.core.di.AndroidDependenciesProvider
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.databaseapi.di.StorageProvider

@FeatureScope
@Component(
    dependencies = [AndroidDependenciesProvider::class],
    modules = [
        RoomModule::class,
        StorageRoomDaoModule::class,
    ]
)
interface StorageRoomComponent : StorageProvider {

    companion object {

        fun init(
            androidDependenciesProvider: AndroidDependenciesProvider
        ): StorageProvider {
            return DaggerStorageRoomComponent.factory()
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
