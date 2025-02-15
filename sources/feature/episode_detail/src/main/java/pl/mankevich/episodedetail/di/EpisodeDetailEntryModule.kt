package pl.mankevich.episodedetail.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.coreui.di.FeatureEntryKey
import pl.mankevich.coreui.navigation.FeatureEntry
import pl.mankevich.episodedetail.EpisodeDetailEntryImpl
import pl.mankevich.episodedetailapi.EpisodeDetailEntry

@Module
interface EpisodeDetailEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(EpisodeDetailEntry::class)
    fun episodeDetailEntry(entry: EpisodeDetailEntryImpl): FeatureEntry<*>
}