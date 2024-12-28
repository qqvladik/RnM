package pl.mankevich.characterdetail.presentation

import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.designsystem.component.Chip
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.RnmCard
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.LocalAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.coreui.utils.characterGenderIconResolver
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.model.Character
import pl.mankevich.model.Episode
import pl.mankevich.model.LocationShort

private val PADDING = 12.dp //TODO Move somewhere
private val PADDING_SMALL = 6.dp

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit = {},
    onLocationClick: (Int) -> Unit = {},
    onEpisodeItemClick: (Int) -> Unit = {},
    onBackPress: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    if (state.character == null) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        CharacterDetailView(
            character = state.character,
            episodes = state.episodes ?: emptyList(),
            onStatusFilterClick = onStatusFilterClick,
            onSpeciesFilterClick = onSpeciesFilterClick,
            onGenderFilterClick = onGenderFilterClick,
            onTypeFilterClick = onTypeFilterClick,
            onOriginClick = onOriginClick,
            onLocationClick = onLocationClick,
            onEpisodeItemClick = onEpisodeItemClick,
            onBackPress = onBackPress,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CharacterDetailView(
    character: Character,
    episodes: List<Episode>,
    onStatusFilterClick: (String) -> Unit,
    onSpeciesFilterClick: (String) -> Unit,
    onGenderFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onOriginClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
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

        Spacer(modifier = Modifier.height(PADDING))

        val fraction = if (isLandscape()) 0.3f else 0.5f
        WithSharedTransitionScope {
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
//                    .background(Red) //For preview purposes
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = "image-${character.image}"
                        ),
                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
                        clipInOverlayDuringTransition = OverlayClip(shape),
                    )
            )
        }

        Spacer(modifier = Modifier.height(PADDING))

        WithSharedTransitionScope {
            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(PADDING))

        CharacterDetail(
            name = "Status",
            value = character.status,
            icon = characterStatusIconResolver(character.status),
            onDetailClick = { onStatusFilterClick(character.status) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        CharacterDetail(
            name = "Species",
            value = character.species,
            icon = characterSpeciesIconResolver(character.species),
            onDetailClick = { onSpeciesFilterClick(character.species) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        CharacterDetail(
            name = "Gender",
            value = character.gender,
            icon = characterGenderIconResolver(character.gender),
            onDetailClick = { onGenderFilterClick(character.gender) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        CharacterDetail(
            name = "Type",
            value = character.type,
            icon = RnmIcons.Blocks,
            onDetailClick = { onTypeFilterClick(character.type) }
        )

        Spacer(modifier = Modifier.height(PADDING))

        Row {
            LocationCard(
                name = "Origin",
                value = character.origin.name,
                icon = RnmIcons.Home,
                onLocationClick = {}
            )

            Spacer(modifier = Modifier.width(PADDING))

            LocationCard(
                name = "Location",
                value = character.location.name,
                icon = RnmIcons.MapPin,
                onLocationClick = {}
            )
        }

        Text("episodes = ${episodes}")
    }
}

@Composable
fun CharacterDetail(
    name: String,
    value: String,
    icon: ImageVector,
    onDetailClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 0.dp)
    ) {
        Text(
            text = "$name:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 0.dp)
        )

        Spacer(modifier = Modifier.width(PADDING))

        Chip(
            label = value,
            icon = icon,
            isSelected = false,
            onClick = { onDetailClick() },
            modifier = Modifier.height(32.dp)
        )
    }
}

@Composable
fun LocationCard(
    name: String,
    value: String,
    icon: ImageVector,
    onLocationClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.height(125.dp)
    ) {
        RnmCard(
            onCardClick = onLocationClick,
            modifier = Modifier
                .width(140.dp)
                .height(100.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Icon(
            painter = rememberVectorPainter(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(40.dp)
        )
    }
}

@ThemePreviews
@Composable
fun CharacterDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            CharacterDetailView(
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
                episodes = emptyList(),
                onStatusFilterClick = {},
                onSpeciesFilterClick = {},
                onGenderFilterClick = {},
                onTypeFilterClick = {},
                onOriginClick = {},
                onLocationClick = {},
                onEpisodeItemClick = {},
                onBackPress = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

