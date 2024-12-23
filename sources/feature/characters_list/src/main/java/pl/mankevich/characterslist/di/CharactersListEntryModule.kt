package pl.mankevich.characterslist.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterslist.CharactersListEntryImpl
import pl.mankevich.characterslistapi.CharactersListEntry
import pl.mankevich.core.di.FeatureEntryKey
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.core.navigation.FeatureEntry

@Module
interface CharactersListEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(CharactersListEntry::class)
    fun charactersListEntry(entry: CharactersListEntryImpl): FeatureEntry
}