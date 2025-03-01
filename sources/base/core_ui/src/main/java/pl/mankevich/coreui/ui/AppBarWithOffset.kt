package pl.mankevich.coreui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun AppBarWithOffset(
    appBarHeight: Dp,
    appBarWithOffset: @Composable ((Float) -> Unit),
    content: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
) {
    var appBarHeightPx = with(LocalDensity.current) { appBarHeight.toPx() }
    var appBarOffsetPx by remember { mutableFloatStateOf(0f) }

    // connection to the nested scroll system and listen to the scroll
    // happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = appBarOffsetPx + delta
                appBarOffsetPx = newOffset.coerceIn(-appBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
        content()
        appBarWithOffset(appBarOffsetPx)
    }
}