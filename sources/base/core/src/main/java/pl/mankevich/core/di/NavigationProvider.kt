package pl.mankevich.core.di

import pl.mankevich.core.navigation.FeatureEntries

interface NavigationProvider {

    val featureEntries: FeatureEntries
}