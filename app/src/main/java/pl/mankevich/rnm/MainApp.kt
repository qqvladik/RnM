package pl.mankevich.rnm

import android.app.Application
import pl.mankevich.dependencies.App
import pl.mankevich.dependencies.DependenciesProvider
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

    private fun getAppComponent(): AppComponent {
        return appComponent ?: AppComponent.init(applicationContext).also { initAppComponent ->
            appComponent = initAppComponent
        }
    }
}
