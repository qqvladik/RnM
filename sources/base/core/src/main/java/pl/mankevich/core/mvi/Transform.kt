package pl.mankevich.core.mvi

/**
 * Represents internal(not visible for UI) action + reducer logic for this action.
 */
interface Transform <TStateWithEffects> {

    fun reduce(current: TStateWithEffects): TStateWithEffects
}