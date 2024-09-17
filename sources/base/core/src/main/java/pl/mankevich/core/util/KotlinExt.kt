package pl.mankevich.core.util

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}