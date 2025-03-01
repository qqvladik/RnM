package pl.mankevich.episodedetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.PADDING_SMALL
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeDetailViewModel,
    onEpisodeFilterClick: (Int) -> Unit,
    onSeasonFilterClick: (Int) -> Unit,
    onBackPress: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    if (state.episode == null) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        EpisodeDetailView(
            episode = state.episode,
            characters = state.characters ?: emptyList(),
            onSeasonFilterClick = onSeasonFilterClick,
            onEpisodeFilterClick = onEpisodeFilterClick,
            onBackPress = onBackPress,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
fun EpisodeDetailView(
    episode: Episode,
    characters: List<Character>,
    onEpisodeFilterClick: (Int) -> Unit,
    onSeasonFilterClick: (Int) -> Unit,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = PADDING)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(PADDING))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            SurfaceIconButton(
                onClick = onBackPress,
                imageVector = RnmIcons.CaretLeft,
                contentDescription = "Show filters",
                iconSize = 20.dp,
                modifier = Modifier.size(40.dp)
            )
        }

//        Spacer(modifier = Modifier.height(PADDING))
//
//        val fraction = if (isLandscape()) 0.3f else 0.5f
//        WithSharedTransitionScope {
//            val shape = CircleShape
//            AsyncImage(
//                model = episode.image,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth(fraction)
//                    .align(CenterHorizontally)
//                    .aspectRatio(1f)
//                    .clip(shape)
////                    .background(Red) //For preview purposes
//                    .sharedElement(
//                        state = rememberSharedContentState(
//                            key = "image-${episode.image}"
//                        ),
//                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
//                        clipInOverlayDuringTransition = OverlayClip(shape),
//                    )
//            )
//        }

        Spacer(modifier = Modifier.height(PADDING))

        WithSharedTransitionScope {
            Text(
                text = episode.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(PADDING))

        Text(
            text = episode.airDate,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier
                .padding(vertical = 0.dp)
                .height(32.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        Detail(
            name = "Season",
            value = episode.season.toString(),
            icon = episodeSeasonIconResolver(episode.season.toString()),
            internalPadding = PADDING,
            onDetailClick = { onSeasonFilterClick(episode.season) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        Detail(
            name = "Episode",
            value = episode.episode.toString(),
            icon = episodeEpisodeIconResolver(episode.episode.toString()),
            internalPadding = PADDING,
            onDetailClick = { onEpisodeFilterClick(episode.episode) }
        )

        Spacer(modifier = Modifier.height(PADDING))

        Text("characters = ${characters}")
    }
}

@ThemePreviews
@Composable
fun EpisodeDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            EpisodeDetailView(
                episode = Episode(
                    id = 1,
                    name = "Rick Sanchez asklncf;lasnc;ljnasc;lj",
                    airDate = "September 10, 2017",
                    season = 3,
                    episode = 7,
                ),
                characters = emptyList(),
                onEpisodeFilterClick = {},
                onSeasonFilterClick = {},
                onBackPress = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

