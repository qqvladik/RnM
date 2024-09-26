package pl.mankevich.core.viewmodel.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
import pl.mankevich.core.viewmodel.ViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactory @Inject constructor(
    private val assistedFactoryMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelAssistedFactory<out ViewModel>>>,
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>, handle: SavedStateHandle): VM {
        val viewModel =
            createAssistedInjectViewModel(modelClass, handle)
                ?: createInjectViewModel(modelClass)
                ?: throw IllegalArgumentException("Unknown model class $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return viewModel as VM
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * Creates ViewModel based on @AssistedInject constructor and its factory
     */
    private fun <T : ViewModel?> createAssistedInjectViewModel(
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): ViewModel? {
        val creator = assistedFactoryMap[modelClass] ?: assistedFactoryMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value?: return null

        return creator.get().create(handle)
    }

    /**
     * Creates ViewModel based on regular Dagger @Inject constructor
     */
    private fun <T : ViewModel?> createInjectViewModel(modelClass: Class<T>): ViewModel? {
        val creator = viewModelProviders[modelClass]
            ?: viewModelProviders.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: return null

        return creator.get()
    }
}