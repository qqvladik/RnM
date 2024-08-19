package pl.mankevich.characterdetail.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.characterdetailapi.CharacterDetailEntry
import pl.mankevich.characterdetail.CharacterDetailEntryImpl
import pl.mankevich.core.di.FeatureEntryKey
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.core.navigation.FeatureEntry

@Module
interface CharacterDetailEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(CharacterDetailEntry::class)
    fun characterDetailEntry(entry: CharacterDetailEntryImpl): FeatureEntry
}