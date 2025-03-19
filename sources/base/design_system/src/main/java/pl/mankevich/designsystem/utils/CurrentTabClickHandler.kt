package pl.mankevich.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.launch

/**
 * Source https://stackoverflow.com/questions/75606566/how-to-handle-bottom-navigation-button-click-2-nd-time-at-inner-screen-using-com
 */
@Composable
fun CurrentTabClickHandler(
    enabled: Boolean = true,
    currentTabClickDispatcher: CurrentTabClickDispatcher = LocalCurrentTabClickDispatcher.current,
    onTabClick: suspend () -> Unit,
) {
    val currentOnTabClick by rememberUpdatedState(onTabClick)
    val currentEnabled by rememberUpdatedState(enabled)
    val coroutineScope = rememberCoroutineScope()
    val handler = remember {
        CurrentTabClickDispatcher.Handler {
            if (currentEnabled) {
                coroutineScope.launch {
                    currentOnTabClick()
                }
            }
        }
    }
    DisposableEffect(Unit) {
        currentTabClickDispatcher.addHandler(handler)
        onDispose {
            currentTabClickDispatcher.removeHandler(handler)
        }
    }
}

class CurrentTabClickDispatcher {
    class Handler(val action: () -> Unit)

    private val handlers = mutableListOf<Handler>()

    fun addHandler(handler: Handler) {
        handlers.add(handler)
    }

    fun removeHandler(handler: Handler) {
        handlers.remove(handler)
    }

    fun currentHandler() =
        handlers.lastOrNull()
}

val LocalCurrentTabClickDispatcher = staticCompositionLocalOf {
    CurrentTabClickDispatcher()
}