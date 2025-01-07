package pl.mankevich.locationdetail.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.coreui.viewmodel.di.ViewModelAssistedFactoryKey
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailViewModel

@Module
interface LocationDetailViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(LocationDetailViewModel::class)]
    fun bindsLocationDetailViewModelFactory(factory: LocationDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}