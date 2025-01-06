package pl.mankevich.coreui.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun Detail(
    name: String,
    value: String,
    icon: ImageVector,
    internalPadding: Dp = 12.dp,
    onDetailClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 0.dp)
    ) {
        Text(
            text = "$name:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 0.dp)
        )

        Spacer(modifier = Modifier.width(internalPadding))

        Chip(
            label = value,
            icon = icon,
            isSelected = false,
            onClick = { onDetailClick() },
            modifier = Modifier.height(32.dp)
        )
    }
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