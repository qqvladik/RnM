package pl.mankevich.rnm.di

import dagger.Component
import pl.mankevich.core.di.providers.DependenciesProvider
import pl.mankevich.rnm.presentation.MainActivity

@Component(
    dependencies = [
        DependenciesProvider::class
    ]
)
interface MainActivityComponent {

    companion object {

        fun init(dependenciesProvider: DependenciesProvider): MainActivityComponent {
            return DaggerMainActivityComponent.builder()
                .dependenciesProvider(dependenciesProvider)
                .build()
        }
    }

    fun inject(mainActivity: MainActivity)
}