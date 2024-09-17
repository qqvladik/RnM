package pl.mankevich.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class MviViewModel<in TAction, TState, TSideEffect>(
    initialStateWithEffects: StateWithEffects<TState, TSideEffect>,
) : ViewModel() {

    private var isInitialized = false

    private val actionFlow = MutableSharedFlow<TAction>(replay = 0, extraBufferCapacity = 64)
    val stateWithEffects: StateFlow<StateWithEffects<TState, TSideEffect>> = actionFlow
            .scan(initialStateWithEffects) { stateWithEffects, action ->
                executeAction(action)
                stateWithEffects.reduce(action)
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialStateWithEffects)

    fun sendAction(action: TAction) {
        if (!actionFlow.tryEmit(action)) {
            viewModelScope.launch { actionFlow.emit(action) }
        }
    }

    fun initializeWithAction(initialAction: TAction) {
        if (!isInitialized) {
            sendAction(initialAction)
            isInitialized = true
        }
    }

    protected abstract fun executeAction(action: TAction)

    protected abstract fun StateWithEffects<TState, TSideEffect>.reduce(action: TAction): StateWithEffects<TState, TSideEffect>
}