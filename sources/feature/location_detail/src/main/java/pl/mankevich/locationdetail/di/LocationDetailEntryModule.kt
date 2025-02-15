package pl.mankevich.locationdetail.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.coreui.di.FeatureEntryKey
import pl.mankevich.coreui.navigation.FeatureEntry
import pl.mankevich.locationdetail.LocationDetailEntryImpl
import pl.mankevich.locationdetailapi.LocationDetailEntry

@Module
interface LocationDetailEntryModule {

    @Binds
    @FeatureScope
    @IntoMap
    @FeatureEntryKey(LocationDetailEntry::class)
    fun locationDetailEntry(entry: LocationDetailEntryImpl): FeatureEntry<*>
}