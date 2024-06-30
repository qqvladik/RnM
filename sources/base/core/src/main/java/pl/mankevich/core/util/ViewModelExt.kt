package pl.mankevich.core.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * It uses LocalViewModelStoreOwner,
 * which means the owner may be Activity, Fragment or NavBackStackEntry
 * For our purposes we are going to use NavBackStackEntry
 */
@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> VM
): VM = viewModel(
    modelClass = VM::class.java,
    key = key,
    factory = object : ViewModelProvider.Factory {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            @Suppress("UNCHECKED_CAST")
            return viewModelInstanceCreator() as VM
        }
    }
)
