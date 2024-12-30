package pl.mankevich.locationslist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListViewModel
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.coreui.viewmodel.di.ViewModelAssistedFactoryKey

@Module
interface LocationsListViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(LocationsListViewModel::class)]
    fun bindsLocationsListViewModelFactory(factory: LocationsListViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}