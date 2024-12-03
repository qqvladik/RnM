package pl.mankevich.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews

@Composable
fun RnmCard(
    onCardClick: () -> Unit = {},
    internalPadding: Dp = 12.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(internalPadding)
        ) {
            content()
        }
    }
}

@ThemePreviews
@Composable
fun RnmCardPreview() {
    RnmTheme {
        RnmCard(
            onCardClick = { },
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {}
    }
}