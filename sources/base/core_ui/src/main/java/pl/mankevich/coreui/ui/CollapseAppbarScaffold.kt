package pl.mankevich.coreui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

@Composable
fun CollapseAppBarScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    var appBarHeightPx by remember { mutableFloatStateOf(0f) }
    var appBarOffsetPx by remember { mutableFloatStateOf(0f) }

    // connection to the nested scroll system and listen to the scroll
    // happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                val newOffset = appBarOffsetPx + delta
                appBarOffsetPx = newOffset.coerceIn(-appBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceables = subcompose("topBar") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(x = 0, y = appBarOffsetPx.toInt()) }
                    .pointerInput(Unit) {} //Helps to prevent clicking on the underlying card elements through spacers
            ) {
                topBar()
            }
        }.fastMap {
            it.measure(looseConstraints)
        }
        val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

        val bodyContentPlaceables = subcompose("content") {
            val innerPadding =
                PaddingValues(
                    top = if (topBarPlaceables.isEmpty()) {
                        0.dp
                    } else {
                        appBarHeightPx = topBarHeight.toFloat()
                        topBarHeight.toDp()
                    },
                )
            Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
                content(innerPadding)
            }
        }.fastMap { it.measure(looseConstraints) }

        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable

            bodyContentPlaceables.fastForEach { it.place(0, 0) }
            topBarPlaceables.fastForEach { it.place(0, 0) }
        }
    }
}