package pl.mankevich.coreui.mvi

data class StateWithEffects<out TState, TSideEffect>(
    val state: TState,
    val sideEffects: SideEffects<TSideEffect> = SideEffects()
)