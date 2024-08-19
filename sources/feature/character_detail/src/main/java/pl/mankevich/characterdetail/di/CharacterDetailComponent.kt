package pl.mankevich.characterdetail.di

import dagger.Component
import pl.mankevich.characterdetail.presentation.CharacterDetailViewModel
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.dependencies.DependenciesProvider

@FeatureScope
@Component(
    dependencies = [DependenciesProvider::class],
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

    fun getViewModel(): CharacterDetailViewModel
}