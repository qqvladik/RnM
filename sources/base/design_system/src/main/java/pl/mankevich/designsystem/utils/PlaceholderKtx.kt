package pl.mankevich.designsystem.utils

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.CARD_CORNERS_SIZE
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.placeholder.PlaceholderHighlight
import pl.mankevich.designsystem.utils.placeholder.placeholder
import pl.mankevich.designsystem.utils.placeholder.shimmer

fun Modifier.placeholderConnecting(
    shape: Dp = CARD_CORNERS_SIZE,
    visible: Boolean = true,
    infiniteTransition: InfiniteTransition? = null,
) = composed {
    this.then(
        placeholder(
            visible = visible,
            shape = RoundedCornerShape(shape),
            color = MaterialTheme.colorScheme.surface,
            highlight = PlaceholderHighlight.shimmer(
                highlightColor = MaterialTheme.colorScheme.onSurface.copy(0.3f),
            ),
            infiniteTransition = infiniteTransition,
        )
    )
}

@ThemePreviews
@Composable
fun PlaceholderPreview() {
    RnmTheme {
        Box(
            modifier = Modifier
                .placeholderConnecting()
                .size(200.dp)
        ) { }
    }
}