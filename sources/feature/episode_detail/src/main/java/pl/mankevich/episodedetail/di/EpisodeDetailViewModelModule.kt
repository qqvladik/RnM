package pl.mankevich.episodedetail.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.coreui.viewmodel.ViewModelAssistedFactory
import pl.mankevich.coreui.viewmodel.di.ViewModelAssistedFactoryKey
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel

@Module
interface EpisodeDetailViewModelModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(EpisodeDetailViewModel::class)]
    fun bindsEpisodeDetailViewModelFactory(factory: EpisodeDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>
}