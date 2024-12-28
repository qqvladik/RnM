package pl.mankevich.coreui.di

import dagger.MapKey
import pl.mankevich.coreui.navigation.FeatureEntry
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)
