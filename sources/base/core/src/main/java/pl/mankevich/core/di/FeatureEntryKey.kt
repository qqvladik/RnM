package pl.mankevich.core.di

import dagger.MapKey
import pl.mankevich.core.navigation.FeatureEntry
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureEntryKey(val value: KClass<out FeatureEntry>)
