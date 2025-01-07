package pl.mankevich.locationdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.coreui.ui.Detail
import pl.mankevich.coreui.utils.PADDING
import pl.mankevich.coreui.utils.PADDING_SMALL
import pl.mankevich.coreui.utils.locationDimensionIconResolver
import pl.mankevich.coreui.utils.locationTypeIconResolver
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.locationdetail.presentation.viewmodel.LocationDetailViewModel
import pl.mankevich.model.Character
import pl.mankevich.model.Location

@Composable
fun LocationDetailScreen(
    viewModel: LocationDetailViewModel,
    onDimensionFilterClick: (String) -> Unit,
    onTypeFilterClick: (String) -> Unit,
    onBackPress: () -> Unit,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    if (state.location == null) {
        LoadingView(modifier = Modifier.fillMaxSize())
    } else {
        LocationDetailView(
            location = state.location,
            characters = state.characters ?: emptyList(),
            onDimensionFilterClick = onDimensionFilterClick,
            onTypeFilterClick = onTypeFilterClick,
            onBackPress = onBackPress,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LocationDetailView(
    location: Location,
    characters: List<Character>,
    onTypeFilterClick: (String) -> Unit,
    onDimensionFilterClick: (String) -> Unit,
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
//                model = location.image,
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
//                            key = "image-${location.image}"
//                        ),
//                        animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
//                        clipInOverlayDuringTransition = OverlayClip(shape),
//                    )
//            )
//        }

        Spacer(modifier = Modifier.height(PADDING))

        WithSharedTransitionScope {
            Text(
                text = location.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(PADDING))

        Detail(
            name = "Type",
            value = location.type,
            icon = locationTypeIconResolver(location.type),
            internalPadding = PADDING,
            onDetailClick = { onTypeFilterClick(location.type) }
        )

        Spacer(modifier = Modifier.height(PADDING_SMALL))

        Detail(
            name = "Dimension",
            value = location.dimension,
            icon = locationDimensionIconResolver(location.dimension),
            internalPadding = PADDING,
            onDetailClick = { onDimensionFilterClick(location.dimension) }
        )

        Spacer(modifier = Modifier.height(PADDING))

        Text("characters = ${characters}")
    }
}

@ThemePreviews
@Composable
fun LocationDetailScreenPreview() {
    RnmTheme {
        WithAnimatedVisibilityScope {
            LocationDetailView(
                location = Location(
                    id = 1,
                    name = "Earth (C-137)",
                    type = "Planet",
                    dimension = "Dimension C-137"
                ),
                characters = emptyList(),
                onTypeFilterClick = {},
                onDimensionFilterClick = {},
                onBackPress = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

