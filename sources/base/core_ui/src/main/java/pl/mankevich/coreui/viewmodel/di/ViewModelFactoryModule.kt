package pl.mankevich.coreui.viewmodel.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.Multibinds
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.coreui.viewmodel.ViewModelFactory

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindsDaggerViewModelFactory(factory: DaggerViewModelFactory): ViewModelFactory

    @Multibinds
    fun bindsViewModelAssistedFactoryMap(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModelAssistedFactory<out ViewModel>>

    @Multibinds
    fun bindsViewModelMap(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>
}