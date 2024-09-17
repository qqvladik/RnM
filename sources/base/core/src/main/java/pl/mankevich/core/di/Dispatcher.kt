package pl.mankevich.core.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val rnmDispatcher: RnmDispatchers)

enum class RnmDispatchers {
    Default,
    IO,
}