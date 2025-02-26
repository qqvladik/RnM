package pl.mankevich.characterdetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailIntent
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailState
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.coreui.ui.CharacterSharedElementKey
import pl.mankevich.coreui.ui.CharacterSharedElementType
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.EpisodeCard
import pl.mankevich.coreui.ui.LocationCard
import pl.mankevich.coreui.utils.characterGenderIconResolver
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.coreui.utils.characterTypeIconResolver
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.LoadingView
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
import pl.mankevich.model.Character
import pl.mankevich.model.Episode
import pl.mankevich.model.LocationShort

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onBackPress: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    CharacterDetailView(
        state = state,
        onStatusFilterClick = onStatusFilterClick,
        onSpeciesFilterClick = onSpeciesFilterClick,
        onGenderFilterClick = onGenderFilterClick,
        onTypeFilterClick = onTypeFilterClick,
        onOriginClick = onOriginClick,
        onLocationClick = onLocationClick,
        onEpisodeItemClick = onEpisodeItemClick,
        onBackPress = onBackPress,
        onCharacterErrorClick = { viewModel.sendIntent(CharacterDetailIntent.LoadCharacter) },
        onEpisodesErrorClick = { viewModel.sendIntent(CharacterDetailIntent.LoadEpisodes) },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CharacterDetailView(
    state: CharacterDetailState,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onBackPress: () -> Unit,
    onCharacterErrorClick: () -> Unit,
    onEpisodesErrorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val character = state.character
    val episodes = state.episodes
    val characterError = state.characterError
    val episodesError = state.episodesError

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
        verticalItemSpacing = PADDING,
        horizontalArrangement = Arrangement.spacedBy(PADDING),
        modifier = modifier
            .padding(horizontal = PADDING)
            .fillMaxSize()
    ) {
        item(span = FullLine) {
            Column(
                modifier = Modifier.fillMaxWidth()
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
            }
        }

        item(
            key = "header",
            span = FullLine
        ) {
            if (characterError != null) {
                ErrorView(
                    error = characterError,
                    modifier = Modifier.fillMaxWidth(),
                    action = onCharacterErrorClick
                )
            } else if (character == null) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            } else {
                WithSharedTransitionScope {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = CharacterSharedElementKey(
                                        id = character.id,
                                        sharedType = CharacterSharedElementType.Background,
                                    )
                                ),
                                animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                            )
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
//                                .background(Red) //For preview purposes
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
                            chipSharedElementKey = CharacterSharedElementKey(
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
                            chipSharedElementKey = CharacterSharedElementKey(
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
                            val isOriginAndLocationSame = character.origin.id == character.location.id
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
                                        .width(140.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        item(span = FullLine) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Episodes",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = PADDING_SMALL)
                )

                Spacer(modifier = Modifier.height(2.dp))

                HorizontalDivider()
            }
        }


        if (episodesError != null) {
            item(span = FullLine) {
                ErrorView(
                    error = episodesError,
                    modifier = Modifier.fillMaxWidth(),
                    action = { onEpisodesErrorClick() }
                )
            }
        } else if (episodes == null) {
            item(span = FullLine) {
                LoadingView(modifier = Modifier.fillMaxWidth())
            }
        } else {
            items(
                items = episodes,
                key = { it.id },
            ) { episode ->
                EpisodeCard(
                    name = episode.name,
                    season = episode.season.toString(),
                    episode = episode.episode.toString(),
                    isFavorite = false,
                    onFavoriteClick = {},
                    onCardClick = { onEpisodeItemClick(episode.id) },
                )
            }

            item(span = FullLine) {}
        }
    }
}

@ThemePreviews
@Composable
fun CharacterDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            CharacterDetailView(
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
                onStatusFilterClick = {},
                onSpeciesFilterClick = {},
                onGenderFilterClick = {},
                onTypeFilterClick = {},
                onOriginClick = {},
                onLocationClick = {},
                onEpisodeItemClick = {},
                onBackPress = {},
                onCharacterErrorClick = {},
                onEpisodesErrorClick = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

