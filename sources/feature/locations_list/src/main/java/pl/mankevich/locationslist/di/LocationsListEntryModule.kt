package pl.mankevich.locationslist.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.coreui.di.FeatureEntryKey
import pl.mankevich.coreui.navigation.FeatureEntry
import pl.mankevich.locationslistapi.LocationsListEntry
import pl.mankevich.locationslist.LocationsListEntryImpl

@Module
interface LocationsListEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(LocationsListEntry::class)
    fun locationsListEntry(entry: LocationsListEntryImpl): FeatureEntry
}