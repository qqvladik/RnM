package pl.mankevich.characterslist.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterslist.CharactersListEntryImpl
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.core.navigation.FeatureEntry
import pl.mankevich.core.di.FeatureEntryKey
import javax.inject.Singleton

@Module
interface CharactersListEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(CharactersListEntry::class)
    fun movieSearchEntry(entry: CharactersListEntryImpl): FeatureEntry
}