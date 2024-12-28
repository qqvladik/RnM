package pl.mankevich.designsystem.utils

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope> {
        error("Must be provided first")
    }

@Composable
fun WithSharedTransitionScope(block: @Composable SharedTransitionScope.() -> Unit) {
    with(LocalSharedTransitionScope.current) {
        block()
    }
}
