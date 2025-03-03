package pl.mankevich.coreui.ui

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.component.Chip
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.placeholderConnecting

@Composable
fun Detail(
    name: String,
    value: String,
    icon: ImageVector,
    internalPadding: Dp = 12.dp,
    chipSharedElementKey: Any? = null,
    chipIconSharedElementKey: Any? = null,
    chipTextSharedElementKey: Any? = null,
    onDetailClick: () -> Unit = {}
) {
    WithSharedTransitionScope {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$name:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.width(internalPadding))

            Chip(
                label = value,
                icon = icon,
                iconSharedElementKey = chipIconSharedElementKey,
                textSharedElementKey = chipTextSharedElementKey,
                isSelected = false,
                onClick = { onDetailClick() },
                modifier = Modifier
                    .height(32.dp)
                    .let { baseModifier ->
                        if (chipSharedElementKey != null) {
                            baseModifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = chipSharedElementKey
                                ),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            )
                        } else {
                            baseModifier
                        }
                    }
            )
        }
    }
}

@Composable
fun DetailPlaceholder(
    infiniteTransition: InfiniteTransition,
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(200.dp)
            .placeholderConnecting(
                shape = CircleShape,
                infiniteTransition = infiniteTransition
            )
    )
}

@ThemePreviews
@Composable
fun CharacterDetailPreview() {
    RnmTheme {
        Detail(
            name = "Status",
            value = "Alive",
            icon = RnmIcons.Pulse,
            onDetailClick = {}
        )
    }
}