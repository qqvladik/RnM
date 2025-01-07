package pl.mankevich.locationdetail.di

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
        LocationDetailViewModelModule::class
    ]
)
interface LocationDetailComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): LocationDetailComponent {
            return DaggerLocationDetailComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): LocationDetailComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}