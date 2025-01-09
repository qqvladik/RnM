package pl.mankevich.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import pl.mankevich.designsystem.R
import pl.mankevich.designsystem.theme.BlueA
import pl.mankevich.designsystem.theme.ThemePreviews

@Composable
fun LikeButton(
    isLiked: Boolean = false,
    onLikeChanged: (Boolean) -> Unit,
    iconLiked: ImageVector = ImageVector.vectorResource(R.drawable.ic_heart_filled),
    iconNotLiked: ImageVector = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
    colorLiked: Color = BlueA,
    colorNotLiked: Color = BlueA,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onLikeChanged(!isLiked) },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isLiked) iconLiked else iconNotLiked,
            contentDescription = "Favorite",
            tint = if (isLiked) colorLiked else colorNotLiked,
        )
    }

}

@ThemePreviews
@Composable
fun LikeButtonPreview() {
    LikeButton(isLiked = true, onLikeChanged = {})
}