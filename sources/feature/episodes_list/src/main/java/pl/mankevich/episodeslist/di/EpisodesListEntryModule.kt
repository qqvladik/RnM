package pl.mankevich.episodeslist.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.coreui.di.FeatureEntryKey
import pl.mankevich.coreui.navigation.FeatureEntry
import pl.mankevich.episodeslist.EpisodesListEntryImpl
import pl.mankevich.episodeslistapi.EpisodesListEntry

@Module
interface EpisodesListEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(EpisodesListEntry::class)
    fun episodesListEntry(entry: EpisodesListEntryImpl): FeatureEntry<*>
}