package pl.mankevich.coreui.di

import pl.mankevich.coreui.navigation.FeatureEntries

interface NavigationProvider {

    val featureEntries: FeatureEntries
}