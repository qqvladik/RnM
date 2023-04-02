package pl.mankevich.rnm

import android.app.Application
import pl.mankevich.core.App
import pl.mankevich.core.di.providers.DependenciesProvider
import pl.mankevich.rnm.di.AppComponent

class MainApp : Application(), App {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
    }

    override fun getDependenciesProvider(): DependenciesProvider {
        return getAppComponent()
    }

    override fun cleanComponent() {
        appComponent = null
    }

    private fun getAppComponent(): AppComponent {
        return appComponent ?: AppComponent.init(applicationContext).also { initAppComponent->
            appComponent = initAppComponent
        }
    }
}