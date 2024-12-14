package pl.mankevich.designsystem.ui

import androidx.compose.foundation.layout.Arrangement
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
import pl.mankevich.designsystem.component.IconText
import pl.mankevich.designsystem.component.LikeButton
import pl.mankevich.designsystem.component.RnmCard
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.Pear
import pl.mankevich.designsystem.theme.Red
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.characterSpeciesIconResolver
import pl.mankevich.designsystem.utils.characterStatusIconResolver

private val PADDING = 12.dp

@Composable
fun CharacterCard(
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
    RnmCard(
        onCardClick = onCardClick,
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(PADDING))

            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
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
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        IconText(
                            text = species,
                            icon = characterSpeciesIconResolver(species),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        IconText(
                            text = origin,
                            icon = RnmIcons.Planet,
                            modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun CharacterCardPreview() {
    RnmTheme {
        CharacterCard(
            modifier = Modifier.width(200.dp),
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
