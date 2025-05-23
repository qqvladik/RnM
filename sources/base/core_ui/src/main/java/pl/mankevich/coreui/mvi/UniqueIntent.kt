package pl.mankevich.coreui.mvi

/**
 * Interface to mark an Intent as unique.
 *
 * This means that everytime a new Intent::class is executed, the reducer will kill
 * all previous jobs for that Intent::class that are still running. Only one job for the same intent
 * type may run at a time.
 */
interface UniqueIntent
