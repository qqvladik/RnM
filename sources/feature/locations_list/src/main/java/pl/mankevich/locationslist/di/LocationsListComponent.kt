package pl.mankevich.locationslist.di

import dagger.Component
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.coreui.viewmodel.ViewModelFactory
import pl.mankevich.coreui.viewmodel.di.ViewModelFactoryModule
import pl.mankevich.dependencies.DependenciesProvider

@FeatureScope
@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        ViewModelFactoryModule::class,
        LocationsListViewModelModule::class
    ]
)
interface LocationsListComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): LocationsListComponent {
            return DaggerLocationsListComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): LocationsListComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}