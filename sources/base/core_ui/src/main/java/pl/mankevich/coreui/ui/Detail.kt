package pl.mankevich.coreui.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope

@Composable
fun Detail(
    name: String,
    value: String,
    icon: ImageVector,
    internalPadding: Dp = 12.dp,
    characterSharedElementKey: CharacterSharedElementKey? = null,
    onDetailClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$name:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.width(internalPadding))

        WithSharedTransitionScope {
            Chip(
                label = value,
                icon = icon,
                isSelected = false,
                onClick = { onDetailClick() },
                modifier = Modifier
                    .height(32.dp)
                    .let { baseModifier ->
                        if (characterSharedElementKey != null) {
                            baseModifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = characterSharedElementKey
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

@ThemePreviews
@Composable
fun CharacterDetailPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            Detail(
                name = "Status",
                value = "Alive",
                icon = RnmIcons.Pulse,
                onDetailClick = {}
            )
        }
    }
}