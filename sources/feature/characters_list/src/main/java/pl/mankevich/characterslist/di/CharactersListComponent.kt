package pl.mankevich.characterslist.di

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
        CharactersListViewModelModule::class
    ]
)
interface CharactersListComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): CharactersListComponent {
            return DaggerCharactersListComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): CharactersListComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}