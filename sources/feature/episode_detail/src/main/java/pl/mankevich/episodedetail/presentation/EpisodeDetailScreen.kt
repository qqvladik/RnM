package pl.mankevich.episodedetail.presentation

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.coreui.ui.CharacterCard
import pl.mankevich.coreui.ui.CharacterCardPlaceholder
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.DetailPlaceholder
import pl.mankevich.coreui.ui.EpisodeSharedElementKey
import pl.mankevich.coreui.ui.EpisodeSharedElementType
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.PADDING_SMALL
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.designsystem.utils.placeholderConnecting
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailIntent
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailState
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeDetailViewModel,
    onEpisodeFilterClick: (Int) -> Unit,
    onSeasonFilterClick: (Int) -> Unit,
    navigateToCharacterDetail: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    EpisodeDetailView(
        id = viewModel.episodeId,
        state = state,
        onSeasonFilterClick = onSeasonFilterClick,
        onEpisodeFilterClick = onEpisodeFilterClick,
        onCharacterItemClick = navigateToCharacterDetail,
        onEpisodeErrorClick = { viewModel.sendIntent(EpisodeDetailIntent.LoadEpisode) },
        onCharactersErrorClick = { viewModel.sendIntent(EpisodeDetailIntent.LoadCharacters) },
        onBackPress = navigateUp,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun EpisodeDetailView(
    id: Int,
    state: EpisodeDetailState,
    onEpisodeFilterClick: (Int) -> Unit,
    onSeasonFilterClick: (Int) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onEpisodeErrorClick: () -> Unit,
    onCharactersErrorClick: () -> Unit,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isCollapsed by remember { derivedStateOf { scrollBehavior.state.overlappedFraction > 0f } }

    WithSharedTransitionScope {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                WithAnimatedVisibilityScope {
                    TopAppBar(
                        title = {
                            if (isCollapsed) {
                                Text(
                                    text = state.episode?.name ?: "",
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = Ellipsis,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        },
                        navigationIcon = {
                            SurfaceIconButton(
                                onClick = onBackPress,
                                imageVector = RnmIcons.CaretLeft,
                                contentDescription = "Back button",
                                iconSize = 20.dp,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            scrolledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                            .animateEnterExit(
                                enter = slideInVertically { -it },
                                exit = slideOutVertically { -it }
                            )
                    )
                }
            }
        ) { paddingValues ->
            val infiniteTransition =
                rememberInfiniteTransition(label = "EpisodeDetailScreen transition")

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                verticalItemSpacing = PADDING,
                horizontalArrangement = Arrangement.spacedBy(PADDING),
                modifier = modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = PADDING,
                        end = PADDING
                    )
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = EpisodeSharedElementKey(
                                id = id,
                                sharedType = EpisodeSharedElementType.Background,
                            )
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    ),
            ) {

                if (state.episodeError != null) {
                    item(span = FullLine) {
                        ErrorView(
                            error = state.episodeError,
                            modifier = Modifier.fillMaxSize(),
                            action = onEpisodeErrorClick
                        )
                    }
                } else if (state.episode == null) {
                    item(span = FullLine) {
                        EpisodeDetailPlaceholder(
                            infiniteTransition = infiniteTransition,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    item(span = FullLine) {
                        EpisodeDetail(
                            episode = state.episode,
                            onSeasonFilterClick = onSeasonFilterClick,
                            onEpisodeFilterClick = onEpisodeFilterClick,
                        )
                    }

                    episodeCharactersItems(
                        characters = state.characters,
                        characterError = state.charactersError,
                        onCharacterErrorClick = onCharactersErrorClick,
                        onCharacterItemClick = onCharacterItemClick,
                        infiniteTransition = infiniteTransition
                    )

                    item(span = FullLine) {}
                }
            }
        }
    }
}

@Composable
fun EpisodeDetailPlaceholder(
    infiniteTransition: InfiniteTransition,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Placeholder 00, 0000",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(CenterHorizontally)
                .placeholderConnecting(
                    shape = CircleShape,
                    infiniteTransition = infiniteTransition,
                )
        )

        Spacer(modifier = Modifier.height(PADDING))

        Text(
            text = "Placeholder text",
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            modifier = Modifier.placeholderConnecting(
                shape = CircleShape,
                infiniteTransition = infiniteTransition,
            )
        )

        Spacer(modifier = Modifier.height(PADDING))

        DetailPlaceholder(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        DetailPlaceholder(infiniteTransition = infiniteTransition)
    }
}

@Composable
fun EpisodeDetail(
    episode: Episode,
    onSeasonFilterClick: (Int) -> Unit,
    onEpisodeFilterClick: (Int) -> Unit,
) {
    WithSharedTransitionScope {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = episode.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = EpisodeSharedElementKey(
                                id = episode.id,
                                sharedType = EpisodeSharedElementType.Name,
                            ),
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )
            )

            Spacer(modifier = Modifier.height(PADDING))

            Text(
                text = episode.airDate,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(PADDING))

            Detail(
                name = "Season",
                value = episode.season.toString(),
                icon = episodeSeasonIconResolver(episode.season.toString()),
                internalPadding = PADDING,
                onDetailClick = { onSeasonFilterClick(episode.season) },
                chipSharedElementKey = EpisodeSharedElementKey(
                    id = episode.id,
                    sharedType = EpisodeSharedElementType.Season,
                ),
            )

            Spacer(modifier = Modifier.height(PADDING_SMALL))

            Detail(
                name = "Episode",
                value = episode.episode.toString(),
                icon = episodeEpisodeIconResolver(episode.episode.toString()),
                internalPadding = PADDING,
                onDetailClick = { onEpisodeFilterClick(episode.episode) },
                chipSharedElementKey = EpisodeSharedElementKey(
                    id = episode.id,
                    sharedType = EpisodeSharedElementType.Episode,
                ),
            )
        }
    }
}

fun LazyStaggeredGridScope.episodeCharactersItems(
    characters: List<Character>?,
    characterError: Throwable?,
    onCharacterItemClick: (Int) -> Unit,
    onCharacterErrorClick: () -> Unit,
    infiniteTransition: InfiniteTransition,
) {
    item(span = FullLine) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Characters",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = PADDING_SMALL)
            )

            Spacer(modifier = Modifier.height(2.dp))

            HorizontalDivider()
        }
    }

    val itemModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(0.75f)

    if (characterError != null) {
        item(span = FullLine) {
            ErrorView(
                error = characterError,
                modifier = Modifier.fillMaxWidth(),
                action = { onCharacterErrorClick() }
            )
        }
    } else if (characters == null || characters.isEmpty()) {
        items(10) {
            CharacterCardPlaceholder(
                infiniteTransition = infiniteTransition,
                modifier = itemModifier
            )
        }
    } else {
        items(
            items = characters,
            key = { it.id },
        ) { character ->
            CharacterCard(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                origin = character.origin.name,
                imageUrl = character.image,
                isFavorite = false,
                onFavoriteClick = {},
                onCardClick = { onCharacterItemClick(character.id) },
                modifier = itemModifier
            )
        }
    }
}

@ThemePreviews
@Composable
fun EpisodeDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            EpisodeDetailView(
                id = 1,
                state = EpisodeDetailState(
                    episode = Episode(
                        id = 1,
                        name = "Rick Sanchez asklncf;lasnc;ljnasc;lj",
                        airDate = "September 10, 2017",
                        season = 3,
                        episode = 7,
                    ),
                    characters = emptyList(),
                ),
                onEpisodeFilterClick = {},
                onSeasonFilterClick = {},
                onCharacterItemClick = {},
                onEpisodeErrorClick = {},
                onCharactersErrorClick = {},
                onBackPress = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

