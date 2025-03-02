package pl.mankevich.coreui.ui

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.designsystem.component.Card
import pl.mankevich.designsystem.component.IconText
import pl.mankevich.designsystem.component.LikeButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.CARD_CORNERS_SIZE
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.Pear
import pl.mankevich.designsystem.theme.Red
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.placeholderConnecting

@Composable
fun CharacterCard(
    id: Int,
    name: String,
    status: String,
    species: String,
    origin: String,
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WithSharedTransitionScope {
        val cardShape = RoundedCornerShape(CARD_CORNERS_SIZE)
        Card(
            onCardClick = onCardClick,
            shape = cardShape,
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = CharacterSharedElementKey(
                            id = id,
                            sharedType = CharacterSharedElementType.Background,
                        )
                    ),
                    animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    clipInOverlayDuringTransition = OverlayClip(cardShape),
                )
        ) {
            Column {
                val imageShape = RoundedCornerShape(8.dp)
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(imageShape)
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = CharacterSharedElementKey(
                                    id = id,
                                    sharedType = CharacterSharedElementType.Image,
                                )
                            ),
                            animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            clipInOverlayDuringTransition = OverlayClip(imageShape),
                        )
                )


                Spacer(modifier = Modifier.height(PADDING))

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = CharacterSharedElementKey(
                                        id = id,
                                        sharedType = CharacterSharedElementType.Name,
                                    )
                                ),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            )
                    )

                    Spacer(modifier = Modifier.height(PADDING))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(modifier = Modifier.weight(1f)) {
                            IconText(
                                text = status,
                                icon = characterStatusIconResolver(status),
                                iconTint = if (status == "Dead") Red else Pear,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = CharacterSharedElementKey(
                                                id = id,
                                                sharedType = CharacterSharedElementType.Status,
                                            )
                                        ),
                                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                                    )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            IconText(
                                text = species,
                                icon = characterSpeciesIconResolver(species),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = CharacterSharedElementKey(
                                                id = id,
                                                sharedType = CharacterSharedElementType.Species,
                                            )
                                        ),
                                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                                    )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            IconText(
                                text = origin,
                                icon = RnmIcons.Home,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = CharacterSharedElementKey(
                                                id = id,
                                                sharedType = CharacterSharedElementType.Origin,
                                            )
                                        ),
                                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                                    )
                            )
                        }

                        var isLiked by remember { mutableStateOf(false) }

//                    LikeButton(
//                        isLiked = isFavorite,
//                        onLikeChanged = { onFavoriteClick() }
//                    )
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
    }
}

@Composable
fun CharacterCardPlaceholder(
    infiniteTransition: InfiniteTransition? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.placeholderConnecting(
            shape = RoundedCornerShape(CARD_CORNERS_SIZE),
            infiniteTransition = infiniteTransition
        )
    )
}

@ThemePreviews
@Composable
fun CharacterCardPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            CharacterCard(
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp),
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                origin = "Earth (C-137)",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                isFavorite = false,
                onFavoriteClick = { /* Handle Favorite Click */ },
                onCardClick = { /* Handle Card Click */ }
            )
        }
    }
}

@ThemePreviews
@Composable
fun CharacterCardPlaceholderPreview() {
    RnmTheme {
        CharacterCardPlaceholder(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp)
        )
    }
}
