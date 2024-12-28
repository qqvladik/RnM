package pl.mankevich.coreui.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    factory: ViewModelFactory
): VM {
    return viewModel {
        factory.create(VM::class.java, createSavedStateHandle())
    }
}


