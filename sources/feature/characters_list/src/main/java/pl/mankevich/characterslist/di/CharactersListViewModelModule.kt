package pl.mankevich.characterslist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.viewmodel.di.ViewModelKey

@Module
interface CharactersListViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(CharactersListViewModel::class)]
    fun bindsCharactersListViewModelFactory(viewModel: CharactersListViewModel): ViewModel
}