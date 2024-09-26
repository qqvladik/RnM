package pl.mankevich.characterdetail.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.core.viewmodel.ViewModelAssistedFactory
import pl.mankevich.core.viewmodel.di.ViewModelAssistedFactoryKey

@Module
interface CharacterDetailViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(CharacterDetailViewModel::class)]
    fun bindsCharacterDetailViewModelFactory(factory: CharacterDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}