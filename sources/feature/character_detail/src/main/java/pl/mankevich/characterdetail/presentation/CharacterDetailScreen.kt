package pl.mankevich.characterdetail.presentation

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailIntent
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailSideEffect
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailState
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.coreui.R
import pl.mankevich.coreui.ui.CharacterSharedElementKey
import pl.mankevich.coreui.ui.CharacterSharedElementType
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.DetailPlaceholder
import pl.mankevich.coreui.ui.EpisodeCard
import pl.mankevich.coreui.ui.EpisodeCardPlaceholder
import pl.mankevich.coreui.ui.LocationCard
import pl.mankevich.coreui.ui.LocationCardPlaceholder
import pl.mankevich.coreui.utils.characterGenderIconResolver
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.coreui.utils.characterTypeIconResolver
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
import pl.mankevich.model.Character
import pl.mankevich.model.Episode
import pl.mankevich.model.LocationShort

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    navigateToCharactersListByStatus: (String) -> Unit,
    navigateToCharactersListBySpecies: (String) -> Unit,
    navigateToCharactersListByGender: (String) -> Unit,
    navigateToCharactersListByType: (String) -> Unit,
    navigateToLocationDetail: (Int) -> Unit,
    navigateToEpisodeDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            when (sideEffect) {
                is CharacterDetailSideEffect.NavigateToCharactersListByStatus ->
                    navigateToCharactersListByStatus(sideEffect.status)

                is CharacterDetailSideEffect.NavigateToCharactersListBySpecies ->
                    navigateToCharactersListBySpecies(sideEffect.species)

                is CharacterDetailSideEffect.NavigateToCharactersListByGender ->
                    navigateToCharactersListByGender(sideEffect.gender)

                is CharacterDetailSideEffect.NavigateToCharactersListByType ->
                    navigateToCharactersListByType(sideEffect.type)

                is CharacterDetailSideEffect.NavigateToLocationDetail ->
                    navigateToLocationDetail(sideEffect.locationId)

                is CharacterDetailSideEffect.NavigateToEpisodeDetail ->
                    navigateToEpisodeDetail(sideEffect.episodeId)

                is CharacterDetailSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    CharacterDetailView(
        id = viewModel.characterId,
        state = state,
        onRefresh = { viewModel.sendIntent(CharacterDetailIntent.LoadCharacterDetail) },
        onStatusFilterClick = { viewModel.sendIntent(CharacterDetailIntent.StatusFilterClick(it)) },
        onSpeciesFilterClick = { viewModel.sendIntent(CharacterDetailIntent.SpeciesFilterClick(it)) },
        onGenderFilterClick = { viewModel.sendIntent(CharacterDetailIntent.GenderFilterClick(it)) },
        onTypeFilterClick = { viewModel.sendIntent(CharacterDetailIntent.TypeFilterClick(it)) },
        onOriginClick = { viewModel.sendIntent(CharacterDetailIntent.LocationItemClick(it)) },
        onLocationClick = { viewModel.sendIntent(CharacterDetailIntent.LocationItemClick(it)) },
        onEpisodeItemClick = { viewModel.sendIntent(CharacterDetailIntent.EpisodeItemClick(it)) },
        onBackClick = { viewModel.sendIntent(CharacterDetailIntent.BackClick) },
        onCharacterErrorClick = { viewModel.sendIntent(CharacterDetailIntent.LoadCharacterDetail) },
        onEpisodesErrorClick = { viewModel.sendIntent(CharacterDetailIntent.LoadEpisodes) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun CharacterDetailView(
    id: Int,
    state: CharacterDetailState,
    onRefresh: () -> Unit,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onCharacterErrorClick: () -> Unit,
    onEpisodesErrorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOffline = state.isOnline != null && !state.isOnline
    val isLoading = state.isOnline == null && state.characterError == null

    val snackbarHostState = remember { SnackbarHostState() }
    val offlineModeWarning = stringResource(id = R.string.offline_mode_warning)
    LaunchedEffect(isOffline, state.characterError) {
        if (isOffline && state.characterError == null) {
            snackbarHostState.showSnackbar(
                message = offlineModeWarning,
                duration = Indefinite,
            )
        }
    }

    WithSharedTransitionScope {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val isOverlapped by remember { derivedStateOf { scrollBehavior.state.overlappedFraction > 0f } }

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
                            if (isOverlapped) {
                                Text(
                                    text = state.character?.name ?: "",
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
                    rememberInfiniteTransition(label = "CharacterDetailScreen transition")

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
                            key = CharacterSharedElementKey(
                                id = id,
                                sharedType = CharacterSharedElementType.Background,
                            )
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )
                ) {

                    if (state.characterError != null) {
                        item(span = FullLine) {
                            ErrorView(
                                error = state.characterError,
                                modifier = Modifier.fillMaxSize(),
                                action = onCharacterErrorClick
                            )
                        }
                    } else if (state.character == null) {
                        item(span = FullLine) {
                            if (isLoading) {
                                CharacterDetailPlaceholder(
                                    infiniteTransition = infiniteTransition,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                EmptyView(modifier = Modifier.fillMaxSize())
                            }
                        }
                    } else {
                        item(span = FullLine) {
                            CharacterDetail(
                                character = state.character,
                                onStatusFilterClick = onStatusFilterClick,
                                onSpeciesFilterClick = onSpeciesFilterClick,
                                onGenderFilterClick = onGenderFilterClick,
                                onTypeFilterClick = onTypeFilterClick,
                                onOriginClick = onOriginClick,
                                onLocationClick = onLocationClick,
                            )
                        }

                        characterEpisodesItems(
                            episodes = state.episodes,
                            episodesError = state.episodesError,
                            onEpisodesErrorClick = onEpisodesErrorClick,
                            onEpisodeItemClick = onEpisodeItemClick,
                            infiniteTransition = infiniteTransition
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterDetailPlaceholder(
    infiniteTransition: InfiniteTransition,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        val fraction = if (isLandscape()) 0.3f else 0.5f

        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .align(CenterHorizontally)
                .aspectRatio(1f)
                .clip(CircleShape)
                .placeholderConnecting(
                    infiniteTransition = infiniteTransition,
                )
        )

        Spacer(modifier = Modifier.height(PADDING))

        Text(
            text = "Placeholder text",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(CenterHorizontally)
                .placeholderConnecting(
                    shape = CircleShape,
                    infiniteTransition = infiniteTransition,
                )

        )

        Spacer(modifier = Modifier.height(PADDING))

        DetailPlaceholder(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        DetailPlaceholder(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        DetailPlaceholder(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        DetailPlaceholder(infiniteTransition = infiniteTransition)

        Spacer(modifier = Modifier.height(PADDING))

        Row {
            LocationCardPlaceholder(
                infiniteTransition = infiniteTransition,
                modifier = Modifier
                    .height(105.dp)
                    .width(140.dp)
            )

            Spacer(modifier = Modifier.width(PADDING))

            LocationCardPlaceholder(
                infiniteTransition = infiniteTransition,
                modifier = Modifier
                    .height(105.dp)
                    .width(140.dp)
            )

        }
    }
}

@Composable
fun CharacterDetail(
    character: Character,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
) {
    WithSharedTransitionScope {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val fraction = if (isLandscape()) 0.3f else 0.5f

            val shape = CircleShape
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .align(CenterHorizontally)
                    .aspectRatio(1f)
                    .clip(shape)
//                .background(Red) //For preview purposes
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = CharacterSharedElementKey(
                                id = character.id,
                                sharedType = CharacterSharedElementType.Image,
                            )
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                        clipInOverlayDuringTransition = OverlayClip(shape),
                    )
            )

            Spacer(modifier = Modifier.height(PADDING))

            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = CharacterSharedElementKey(
                                id = character.id,
                                sharedType = CharacterSharedElementType.Name,
                            )
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )

            )

            Spacer(modifier = Modifier.height(PADDING))

            Detail(
                name = "Status",
                value = character.status,
                icon = characterStatusIconResolver(character.status),
                internalPadding = PADDING,
                chipIconTextSharedElementKey = CharacterSharedElementKey(
                    id = character.id,
                    sharedType = CharacterSharedElementType.Status,
                ),
                onDetailClick = { onStatusFilterClick(character.status) }
            )

            Spacer(modifier = Modifier.height(PADDING_SMALL))

            Detail(
                name = "Species",
                value = character.species,
                icon = characterSpeciesIconResolver(character.species),
                internalPadding = PADDING,
                chipIconTextSharedElementKey = CharacterSharedElementKey(
                    id = character.id,
                    sharedType = CharacterSharedElementType.Species,
                ),
                onDetailClick = { onSpeciesFilterClick(character.species) }
            )

            Spacer(modifier = Modifier.height(PADDING_SMALL))

            Detail(
                name = "Gender",
                value = character.gender,
                icon = characterGenderIconResolver(character.gender),
                internalPadding = PADDING,
                onDetailClick = { onGenderFilterClick(character.gender) }
            )

            Spacer(modifier = Modifier.height(PADDING_SMALL))

            Detail(
                name = "Type",
                value = character.type,
                icon = characterTypeIconResolver(character.type),
                internalPadding = PADDING,
                onDetailClick = { onTypeFilterClick(character.type) }
            )

            Spacer(modifier = Modifier.height(PADDING))

            Row {
                val isOriginAndLocationSame =
                    character.origin.id == character.location.id
                LocationCard(
                    id = character.origin.id,
                    type = if (!isOriginAndLocationSame) "Origin" else "Origin/Location",
                    name = character.origin.name,
                    icon = RnmIcons.Home,
                    isLikeable = false,
                    isClickable = character.origin.id != null,
                    onLocationClick = {
                        character.origin.id?.let { onOriginClick(it) }
                    },
                    modifier = Modifier
                        .height(105.dp)
                        .width(140.dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = CharacterSharedElementKey(
                                    id = character.id,
                                    sharedType = CharacterSharedElementType.Origin,
                                )
                            ),
                            animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                        )
                )

                if (!isOriginAndLocationSame) {
                    Spacer(modifier = Modifier.width(PADDING))

                    LocationCard(
                        id = character.location.id,
                        type = "Location",
                        name = character.location.name,
                        icon = RnmIcons.MapPin,
                        isLikeable = false,
                        isClickable = character.location.id != null,
                        onLocationClick = {
                            character.location.id?.let { onLocationClick(it) }
                        },
                        modifier = Modifier
                            .height(105.dp)
                            .width(140.dp)
                    )
                }
            }
        }
    }
}

fun LazyStaggeredGridScope.characterEpisodesItems(
    episodes: List<Episode>?,
    episodesError: Throwable?,
    onEpisodesErrorClick: () -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    infiniteTransition: InfiniteTransition
) {
    item(span = FullLine) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Episodes",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = PADDING_SMALL)
            )

            Spacer(modifier = Modifier.height(2.dp))

            HorizontalDivider()
        }
    }

    val itemModifier = Modifier
        .height(80.dp)
        .fillMaxWidth()

    if (episodesError != null) {
        item(span = FullLine) {
            ErrorView(
                error = episodesError,
                modifier = Modifier.fillMaxWidth(),
                action = { onEpisodesErrorClick() }
            )
        }
    } else if (episodes == null) {
        items(10) {
            EpisodeCardPlaceholder(
                infiniteTransition = infiniteTransition,
                modifier = itemModifier
            )
        }
    } else if (episodes.isEmpty()) {
        item(span = FullLine) {
            EmptyView(
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        items(
            items = episodes,
            key = { it.id },
        ) { episode ->
            EpisodeCard(
                id = episode.id,
                name = episode.name,
                season = episode.season.toString(),
                episode = episode.episode.toString(),
                isFavorite = false,
                onFavoriteClick = {},
                onCardClick = { onEpisodeItemClick(episode.id) },
                modifier = itemModifier.animateItem()
            )
        }
    }
}

@ThemePreviews
@Composable
fun CharacterDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            CharacterDetailView(
                id = 1,
                state = CharacterDetailState(
                    characterError = null,
                    episodesError = null,
                    character = Character(
                        id = 1,
                        name = "Rick Sanchez asklncf;lasnc;ljnasc;lj",
                        status = "Alive",
                        species = "Human",
                        type = "",
                        origin = LocationShort(
                            id = 1,
                            name = "Earth (C-137)",
                        ),
                        location = LocationShort(
                            id = 20,
                            name = "Earth (Replacement Dimension)",
                        ),
                        gender = "Male",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    ),
                    episodes = listOf(
                        Episode(
                            id = 1,
                            name = "Pilot",
                            airDate = "December 2, 2013",
                            episode = 1,
                            season = 1,
                        ),
                        Episode(
                            id = 2,
                            name = "Lawnmower Dog",
                            airDate = "December 9, 2013",
                            episode = 2,
                            season = 1,
                        )
                    ),
                ),
                onRefresh = {},
                onStatusFilterClick = {},
                onSpeciesFilterClick = {},
                onGenderFilterClick = {},
                onTypeFilterClick = {},
                onOriginClick = {},
                onLocationClick = {},
                onEpisodeItemClick = {},
                onBackClick = {},
                onCharacterErrorClick = {},
                onEpisodesErrorClick = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

