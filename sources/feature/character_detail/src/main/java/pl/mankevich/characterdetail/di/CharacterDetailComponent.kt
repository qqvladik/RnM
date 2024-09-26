package pl.mankevich.characterdetail.di

import dagger.Component
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.core.viewmodel.ViewModelFactory
import pl.mankevich.core.viewmodel.di.ViewModelFactoryModule
import pl.mankevich.dependencies.DependenciesProvider

@FeatureScope
@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        ViewModelFactoryModule::class,
        CharacterDetailViewModelModule::class
    ]
)
interface CharacterDetailComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): CharacterDetailComponent {
            return DaggerCharacterDetailComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): CharacterDetailComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}