package pl.mankevich.episodeslist.di

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
        EpisodesListViewModelModule::class
    ]
)
interface EpisodesListComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): EpisodesListComponent {
            return DaggerEpisodesListComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): EpisodesListComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}