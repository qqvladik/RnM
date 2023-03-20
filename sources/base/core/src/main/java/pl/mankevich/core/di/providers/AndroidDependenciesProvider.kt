package pl.mankevich.core.di.providers

import android.content.Context
import pl.mankevich.core.di.qualifiers.ApplicationContext

interface AndroidDependenciesProvider {

    @ApplicationContext
    fun provideContext(): Context
}