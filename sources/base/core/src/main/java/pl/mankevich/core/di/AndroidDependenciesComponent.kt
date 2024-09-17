package pl.mankevich.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component
interface AndroidDependenciesComponent : AndroidDependenciesProvider {

    companion object {

        fun init(context: Context): AndroidDependenciesProvider {
            return DaggerAndroidDependenciesComponent.factory()
                .create(context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance appContext: Context): AndroidDependenciesProvider
    }
}