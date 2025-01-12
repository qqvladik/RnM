package pl.mankevich.coreui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.component.Card
import pl.mankevich.designsystem.component.IconText
import pl.mankevich.designsystem.component.LikeButton
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope

@Composable
fun EpisodeCard(
    name: String,
    season: String,
    episode: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onCardClick = onCardClick,
        modifier = modifier
    ) {
        Column {
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
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        IconText(
                            text = season,
                            icon = episodeSeasonIconResolver(season),
                        )

                        VerticalDivider(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(horizontal = 4.dp)
                        )

                        IconText(
                            text = episode,
                            icon = episodeEpisodeIconResolver(episode),
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

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

@ThemePreviews
@Composable
fun EpisodeCardPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            EpisodeCard(
                modifier = Modifier.width(200.dp),
                name = "Pilot asdfklsnakljdsnfkljsdbnfkls",
                season = "1",
                episode = "1",
                isFavorite = false,
                onFavoriteClick = { /* Handle Favorite Click */ },
                onCardClick = { /* Handle Card Click */ }
            )
        }
    }
}