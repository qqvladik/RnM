package pl.mankevich.characterslist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
import pl.mankevich.core.viewmodel.di.ViewModelAssistedFactoryKey

@Module
interface CharactersListViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(CharactersListViewModel::class)]
    fun bindsCharactersListViewModelFactory(factory: CharactersListViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}