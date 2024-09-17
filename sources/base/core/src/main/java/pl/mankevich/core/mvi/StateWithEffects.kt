package pl.mankevich.core.mvi

data class StateWithEffects<out TState, TSideEffect>(
    val state: TState,
    val sideEffects: SideEffects<TSideEffect> = SideEffects()
)