package pl.mankevich.characterdetail.presentation

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
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.ui.LocationCard
import pl.mankevich.coreui.utils.PADDING
import pl.mankevich.coreui.utils.PADDING_SMALL
import pl.mankevich.coreui.utils.characterGenderIconResolver
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.coreui.utils.characterTypeIconResolver
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
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

        Detail(
            name = "Status",
            value = character.status,
            icon = characterStatusIconResolver(character.status),
            internalPadding = PADDING,
            onDetailClick = { onStatusFilterClick(character.status) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        Detail(
            name = "Species",
            value = character.species,
            icon = characterSpeciesIconResolver(character.species),
            internalPadding = PADDING,
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
            LocationCard(
                name = "Origin",
                value = character.origin.name,
                icon = RnmIcons.Home,
                isLikeable = false,
                isClickable = character.origin.id != null,
                onLocationClick = {
                    character.origin.id?.let { onOriginClick(it) }
                },
                modifier = Modifier
                    .height(125.dp)
                    .width(140.dp)
            )

            Spacer(modifier = Modifier.width(PADDING))

            LocationCard(
                name = "Location",
                value = character.location.name,
                icon = RnmIcons.MapPin,
                isLikeable = false,
                isClickable = character.location.id != null,
                onLocationClick = {
                    character.location.id?.let { onLocationClick(it) }
                },
                modifier = Modifier
                    .height(125.dp)
                    .width(140.dp)
            )
        }

        Text("episodes = ${episodes}")
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

