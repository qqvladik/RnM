package pl.mankevich.locationdetail.presentation

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.coreui.ui.CharacterCard
import pl.mankevich.coreui.ui.CharacterCardPlaceholder
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.DetailPlaceholder
import pl.mankevich.coreui.ui.LocationSharedElementKey
import pl.mankevich.coreui.ui.LocationSharedElementType
import pl.mankevich.coreui.utils.locationDimensionIconResolver
import pl.mankevich.coreui.utils.locationTypeIconResolver
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
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailIntent
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailState
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailViewModel
import pl.mankevich.model.Character
import pl.mankevich.model.Location

@Composable
fun LocationDetailScreen(
    viewModel: LocationDetailViewModel,
    onDimensionFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    LocationDetailView(
        id = viewModel.locationId,
        state = state,
        onDimensionFilterClick = onDimensionFilterClick,
        onTypeFilterClick = onTypeFilterClick,
        onCharacterItemClick = onCharacterItemClick,
        onLocationErrorClick = { viewModel.sendIntent(LocationDetailIntent.LoadLocation) },
        onCharactersErrorClick = { viewModel.sendIntent(LocationDetailIntent.LoadCharacters) },
        onBackPress = onBackPress,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun LocationDetailView(
    id: Int,
    state: LocationDetailState,
    onTypeFilterClick: (String) -> Unit,
    onDimensionFilterClick: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onLocationErrorClick: () -> Unit,
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
                                    text = state.location?.name ?: "",
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
        ) { values ->
            val infiniteTransition =
                rememberInfiniteTransition(label = "LocationDetailScreen transition")

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                verticalItemSpacing = PADDING,
                horizontalArrangement = Arrangement.spacedBy(PADDING),
                modifier = modifier
                    .padding(values)
                    .padding(horizontal = PADDING)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = LocationSharedElementKey(
                                id = id,
                                sharedType = LocationSharedElementType.Background,
                            )
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )
            ) {

                if (state.locationError != null) {
                    item(span = FullLine) {
                        ErrorView(
                            error = state.locationError,
                            modifier = Modifier.fillMaxSize(),
                            action = onLocationErrorClick
                        )
                    }
                } else if (state.location == null) {
                    item(span = FullLine) {
                        LocationDetailPlaceholder(
                            infiniteTransition = infiniteTransition,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    item(span = FullLine) {
                        LocationDetail(
                            location = state.location,
                            onTypeFilterClick = onTypeFilterClick,
                            onDimensionFilterClick = onDimensionFilterClick,
                        )
                    }

                    locationCharactersItems(
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
fun LocationDetailPlaceholder(
    infiniteTransition: InfiniteTransition,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
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
    }
}

@Composable
fun LocationDetail(
    location: Location,
    onTypeFilterClick: (String) -> Unit,
    onDimensionFilterClick: (String) -> Unit,
) {
    WithSharedTransitionScope {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = location.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = LocationSharedElementKey(
                                id = location.id,
                                sharedType = LocationSharedElementType.Name,
                            ),
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                    )
            )

            Spacer(modifier = Modifier.height(PADDING))

            Detail(
                name = "Type",
                value = location.type,
                icon = locationTypeIconResolver(location.type),
                internalPadding = PADDING,
                onDetailClick = { onTypeFilterClick(location.type) },
                chipIconSharedElementKey = LocationSharedElementKey(
                    id = location.id,
                    sharedType = LocationSharedElementType.Icon,
                ),
                chipTextSharedElementKey = LocationSharedElementKey(
                    id = location.id,
                    sharedType = LocationSharedElementType.Type,
                ),
            )

            Spacer(modifier = Modifier.height(PADDING_SMALL))

            Detail(
                name = "Dimension",
                value = location.dimension,
                icon = locationDimensionIconResolver(location.dimension),
                internalPadding = PADDING,
                onDetailClick = { onDimensionFilterClick(location.dimension) }
            )
        }
    }
}

fun LazyStaggeredGridScope.locationCharactersItems(
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
fun LocationDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            LocationDetailView(
                id = 1,
                state = LocationDetailState(
                    location = Location(
                        id = 1,
                        name = "Earth (C-137)",
                        type = "Planet",
                        dimension = "Dimension C-137"
                    ),
                    characters = emptyList()
                ),
                onTypeFilterClick = {},
                onDimensionFilterClick = {},
                onCharacterItemClick = {},
                onLocationErrorClick = {},
                onCharactersErrorClick = {},
                onBackPress = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

