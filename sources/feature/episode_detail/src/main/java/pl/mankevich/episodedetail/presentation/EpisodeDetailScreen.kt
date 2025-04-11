package pl.mankevich.episodedetail.presentation

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.coreui.R
import pl.mankevich.coreui.ui.CharacterCard
import pl.mankevich.coreui.ui.CharacterCardPlaceholder
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.DetailPlaceholder
import pl.mankevich.coreui.ui.EpisodeSharedElementKey
import pl.mankevich.coreui.ui.EpisodeSharedElementType
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
import pl.mankevich.designsystem.component.EmptyView
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
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailSideEffect
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailState
import pl.mankevich.episodedetail.presentation.viewmodel.EpisodeDetailViewModel
import pl.mankevich.model.Character
import pl.mankevich.model.Episode

@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeDetailViewModel,
    navigateToEpisodesListBySeason: (Int) -> Unit,
    navigateToEpisodesListByEpisode: (Int) -> Unit,
    navigateToCharacterDetail: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            when (sideEffect) {
                is EpisodeDetailSideEffect.NavigateToEpisodesListBySeason ->
                    navigateToEpisodesListBySeason(sideEffect.season)

                is EpisodeDetailSideEffect.NavigateToEpisodesListByEpisode ->
                    navigateToEpisodesListByEpisode(sideEffect.episode)

                is EpisodeDetailSideEffect.NavigateToCharacterDetail ->
                    navigateToCharacterDetail(sideEffect.characterId)

                is EpisodeDetailSideEffect.NavigateBack ->
                    navigateUp()
            }
        }
    }

    EpisodeDetailView(
        id = viewModel.episodeId,
        state = state,
        onRefresh = { viewModel.sendIntent(EpisodeDetailIntent.LoadEpisodeDetail) },
        onSeasonFilterClick = { viewModel.sendIntent(EpisodeDetailIntent.SeasonFilterClick(it)) },
        onEpisodeFilterClick = { viewModel.sendIntent(EpisodeDetailIntent.EpisodeFilterClick(it)) },
        onCharacterItemClick = { viewModel.sendIntent(EpisodeDetailIntent.CharacterItemClick(it)) },
        onEpisodeErrorClick = { viewModel.sendIntent(EpisodeDetailIntent.LoadEpisodeDetail) },
        onCharactersErrorClick = { viewModel.sendIntent(EpisodeDetailIntent.LoadCharacters) },
        onBackClick = { viewModel.sendIntent(EpisodeDetailIntent.BackClick) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun EpisodeDetailView(
    id: Int,
    state: EpisodeDetailState,
    onRefresh: () -> Unit,
    onEpisodeFilterClick: (Int) -> Unit,
    onSeasonFilterClick: (Int) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onEpisodeErrorClick: () -> Unit,
    onCharactersErrorClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOffline = state.isOnline != null && !state.isOnline
    val isLoading = state.isOnline == null && state.episodeError == null

    val snackbarHostState = remember { SnackbarHostState() }
    val offlineModeWarning = stringResource(id = R.string.offline_mode_warning)
    LaunchedEffect(isOffline, state.episodeError) {
        if (isOffline && state.episodeError == null) {
            snackbarHostState.showSnackbar(
                message = offlineModeWarning,
                duration = Indefinite,
            )
        }
    }

    WithSharedTransitionScope {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val isCollapsed by remember { derivedStateOf { scrollBehavior.state.overlappedFraction > 0f } }

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing,
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
                                onClick = onBackClick,
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
                        windowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
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

            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = onRefresh,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(top = paddingValues.calculateTopPadding()),
            ) {

                val infiniteTransition =
                    rememberInfiniteTransition(label = "EpisodeDetailScreen transition")

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                    verticalItemSpacing = PADDING,
                    horizontalArrangement = Arrangement.spacedBy(PADDING),
                    contentPadding = PaddingValues(
                        bottom = paddingValues.calculateBottomPadding() + PADDING,
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + PADDING,
                        end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + PADDING
                    ),
                    modifier = modifier.sharedBounds(
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
                            if (isLoading) {
                                EpisodeDetailPlaceholder(
                                    infiniteTransition = infiniteTransition,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                EmptyView(modifier = Modifier.fillMaxSize())
                            }
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
                    }
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
                chipIconTextSharedElementKey = EpisodeSharedElementKey(
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
                chipIconTextSharedElementKey = EpisodeSharedElementKey(
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
    } else if (characters == null) {
        items(10) {
            CharacterCardPlaceholder(
                infiniteTransition = infiniteTransition,
                modifier = itemModifier
            )
        }
    } else if (characters.isEmpty()) {
        item(span = FullLine) {
            EmptyView(
                modifier = Modifier.fillMaxWidth()
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
                modifier = itemModifier.animateItem()
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
                onRefresh = {},
                onEpisodeFilterClick = {},
                onSeasonFilterClick = {},
                onCharacterItemClick = {},
                onEpisodeErrorClick = {},
                onCharactersErrorClick = {},
                onBackClick = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

