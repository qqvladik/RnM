package pl.mankevich.coreimpl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.mankevich.core.di.qualifiers.ApplicationContext

@Module
class AndroidDependenciesModule(
    private val context: Context
) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return context
    }
}