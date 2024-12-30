package pl.mankevich.episodeslist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.coreui.viewmodel.di.ViewModelAssistedFactoryKey
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel

@Module
interface EpisodesListViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(EpisodesListViewModel::class)]
    fun bindsCharactersListViewModelFactory(factory: EpisodesListViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}