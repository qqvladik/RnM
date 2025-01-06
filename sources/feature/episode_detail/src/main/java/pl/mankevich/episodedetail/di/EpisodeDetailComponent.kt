package pl.mankevich.episodedetail.di

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
        EpisodeDetailViewModelModule::class
    ]
)
interface EpisodeDetailComponent {

    companion object {

        fun init(
            dependenciesProvider: DependenciesProvider
        ): EpisodeDetailComponent {
            return DaggerEpisodeDetailComponent.factory()
                .create(dependenciesProvider = dependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(dependenciesProvider: DependenciesProvider): EpisodeDetailComponent
    }

    fun getViewModelFactory(): ViewModelFactory
}