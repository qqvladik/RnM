package pl.mankevich.coreui.mvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import pl.mankevich.coreui.mvi.StateWithEffects

/**
 * The [MviContainer] is a composable function for implementing the MVI in Jetpack Compose, providing a convenient way to observe the state of your ViewModel.
 * The MVIContainer should be used in non-screen composable with a ViewModel attached, such as a ViewPager page that will be
 * included inside of a parent screen.
 * @param stateWithEffectsFlow The current state of the MVI ViewModel. This parameter must be of type [StateFlow]<[StateWithEffects]<TViewState, TSideEffect>>, where TViewState is the type
 * of the view state and TSideEffect is the type of the side effect.
 * @param onSideEffect A lambda function that is called when a side effect is emitted by the MVI ViewModel. The TSideEffect parameter of the function is
 * the emitted side effect.
 * @param onViewState A composable function that takes the current view state as its parameter and composes the UI for the container.
 */
@Composable
fun <TViewState, TSideEffect> MviContainer(
    stateWithEffectsFlow: StateFlow<StateWithEffects<TViewState, TSideEffect>>,
    onSideEffect: (sideEffect: TSideEffect) -> Unit,
    onViewState: @Composable (viewState: TViewState) -> Unit,
) {
    val stateWithEffects by stateWithEffectsFlow.collectAsStateWithLifecycle()

    onViewState(stateWithEffects.state)

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            onSideEffect(sideEffect)
        }
    }
}
