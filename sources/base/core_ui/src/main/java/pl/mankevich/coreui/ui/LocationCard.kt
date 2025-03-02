package pl.mankevich.coreui.ui

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.component.Card
import pl.mankevich.designsystem.component.LikeButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.CARD_CORNERS_SIZE
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.placeholderConnecting

@Composable
fun LocationCard(
    id: Int?,
    type: String,
    name: String,
    icon: ImageVector,
    isLikeable: Boolean = true,
    isClickable: Boolean = true,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onLocationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    WithSharedTransitionScope {
        Box(
            modifier = modifier
        ) {
            Column {
                Spacer(modifier = Modifier.height(25.dp))
                Card(
                    onCardClick = onLocationClick,
                    isClickable = isClickable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = LocationSharedElementKey(
                                    id = id,
                                    sharedType = LocationSharedElementType.Background
                                ),
                            ),
                            animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = type,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = LocationSharedElementKey(
                                        id = id,
                                        sharedType = LocationSharedElementType.Type,
                                    ),
                                ),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = name,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = LocationSharedElementKey(
                                        id = id,
                                        sharedType = LocationSharedElementType.Name,
                                    ),
                                ),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        if (isLikeable) {
                            var isLiked by remember { mutableStateOf(false) }
//                            LikeButton(
//                                isLiked = isFavorite,
//                                onLikeChanged = { onFavoriteClick() }
//                            )
                            LikeButton(
                                isLiked = isLiked,
                                onLikeChanged = {
                                    isLiked = it
                                },
                                modifier = Modifier.size(LIKE_SIZE)
                            )
                        }
                    }
                }
            }


            Icon(
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(40.dp)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = LocationSharedElementKey(
                                id = id,
                                sharedType = LocationSharedElementType.Icon,
                            ),
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )
            )
        }
    }
}

@Composable
fun LocationCardPlaceholder(
    infiniteTransition: InfiniteTransition? = null,
    modifier: Modifier = Modifier //wrapContentHeight with data = ~135.dp
) {
    Box(
        modifier = modifier
    ) {
        Column {
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = modifier.placeholderConnecting(
                    shape = RoundedCornerShape(CARD_CORNERS_SIZE),
                    infiniteTransition = infiniteTransition
                )
            )
        }
    }
}

@ThemePreviews
@Composable
fun LocationCardPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            LocationCard(
                id = 1,
                type = "Origin",
                name = "Earth (C-137)",
                icon = RnmIcons.Planet,
                onLocationClick = {},
                isLikeable = true,
                isClickable = true,
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
fun LocationCardNotLikeablePreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            LocationCard(
                id = 1,
                type = "Origin",
                name = "Earth (C-137)",
                icon = RnmIcons.MapPin,
                onLocationClick = {},
                isLikeable = false,
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
fun LocationCardNotClickablePreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            LocationCard(
                id = 1,
                type = "Origin",
                name = "Earth (C-137)dklfc;ladnsfjak;las",
                icon = RnmIcons.MapPin,
                onLocationClick = {},
                isClickable = false,
                modifier = Modifier
                    .height(135.dp)
                    .width(140.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
fun LocationCardPlaceholderPreview() {
    RnmTheme {
        LocationCardPlaceholder(
            modifier = Modifier
                .height(135.dp)
                .width(140.dp)
        )
    }
}