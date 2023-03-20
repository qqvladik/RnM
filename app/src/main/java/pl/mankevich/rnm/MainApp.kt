package pl.mankevich.rnm

import android.app.Application
import pl.mankevich.rnm.di.AppComponent

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponent.init(applicationContext).inject(this)
    }
}