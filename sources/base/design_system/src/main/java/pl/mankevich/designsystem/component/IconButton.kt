package pl.mankevich.designsystem.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews

@Composable
fun IconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    iconSize: Dp = 16.dp,
    modifier: Modifier = Modifier.size(32.dp)
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier.aspectRatio(1f)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(iconSize)
        )
    }
}

@ThemePreviews
@Composable
fun IconButtonPreview() {
    RnmTheme {
        IconButton(
            onClick = {},
            imageVector = Icons.Default.Add,
            contentDescription = "Add filter",
        )
    }
}