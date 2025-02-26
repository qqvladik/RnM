package pl.mankevich.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope

@Composable
fun IconText(
    text: String,
    icon: ImageVector,
    iconSize: Dp = 16.dp,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    iconSharedElementKey: Any? = null,
    textSharedElementKey: Any? = null,
    modifier: Modifier = Modifier
) {
    WithSharedTransitionScope {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(iconSize)
                    .let { baseModifier ->
                        if (iconSharedElementKey != null) {
                            baseModifier.sharedElement(
                                state = rememberSharedContentState(key = iconSharedElementKey),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current
                            )
                        } else {
                            baseModifier
                        }
                    }
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = text,
                color = textColor,
                style = textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.let { baseModifier ->
                    if (textSharedElementKey != null) {
                        baseModifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(key = textSharedElementKey),
                            animatedVisibilityScope = LocalAnimatedVisibilityScope.current
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
fun IconTextPreview() {
    RnmTheme {
        IconText(
            text = "Favorite",
            icon = Icons.Default.Favorite,
            modifier = Modifier
        )
    }
}