package pl.mankevich.characterslist.presentation

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListIntent
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListState
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.ui.CharacterCard
import pl.mankevich.designsystem.ui.filter.FilterGroup
import pl.mankevich.designsystem.ui.filter.FilterView
import pl.mankevich.designsystem.utils.characterGenderIconResolver
import pl.mankevich.designsystem.utils.characterSpeciesIconResolver
import pl.mankevich.designsystem.utils.characterStatusIconResolver
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.model.LocationShort

private val PADDING = 12.dp

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(sideEffect, onCharacterItemClick)
        }
    }

    CharactersListView(
        state = state,
        onSearchChange = { viewModel.sendIntent(CharactersListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(CharactersListIntent.NameChanged("")) },
        onStatusSelected = { viewModel.sendIntent(CharactersListIntent.StatusChanged(it)) },
        onSpeciesSelected = { viewModel.sendIntent(CharactersListIntent.SpeciesChanged(it)) },
        onGenderSelected = { viewModel.sendIntent(CharactersListIntent.GenderChanged(it)) },
        onTypeSelected = { viewModel.sendIntent(CharactersListIntent.TypeChanged(it)) },
        onCharacterItemClick = onCharacterItemClick,
        onBackPress = onBackPress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CharactersListView(
    state: CharactersListState,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onStatusSelected: (String) -> Unit,
    onSpeciesSelected: (String) -> Unit,
    onGenderSelected: (String) -> Unit,
    onTypeSelected: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val pagingCharacterItems = state.characters.collectAsLazyPagingItems()

    Column(modifier = modifier.padding(horizontal = PADDING)) {
        Spacer(modifier = Modifier.height(PADDING))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (onBackPress != null) {
                SurfaceIconButton(
                    onClick = onBackPress,
                    imageVector = RnmIcons.CaretLeft,
                    contentDescription = "Show filters",
                    iconSize = 20.dp,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(PADDING))
            }

            SearchField(
                value = state.characterFilter.name,
                onValueChange = onSearchChange,
                onClearClick = onSearchClear,
                placeholder = "Search...",
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
            )
        }



        Spacer(modifier = Modifier.height(PADDING))

        FilterView(
            name = "Characters filter",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Status",
                    selected = state.characterFilter.status,
                    labelList = state.statusLabelList,
                    isListFinished = true,
                    resolveIcon = { characterStatusIconResolver(it) },
                    onSelectedChanged = onStatusSelected,
                ),
                FilterGroup(
                    name = "Species",
                    selected = state.characterFilter.species,
                    labelList = state.speciesLabelList,
                    isListFinished = false,
                    resolveIcon = { characterSpeciesIconResolver(it) },
                    onSelectedChanged = onSpeciesSelected,
                ),
                FilterGroup(
                    name = "Gender",
                    selected = state.characterFilter.gender,
                    labelList = state.genderLabelList,
                    isListFinished = true,
                    resolveIcon = { characterGenderIconResolver(it) },
                    onSelectedChanged = onGenderSelected,
                ),
                FilterGroup(
                    name = "Type",
                    selected = state.characterFilter.type,
                    labelList = state.typeLabelList,
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Blocks },
                    onSelectedChanged = onTypeSelected,
                )
            ),
            modifier = Modifier.height(32.dp)
        )

        Spacer(modifier = Modifier.height(PADDING))

        /*When use pager with remoteMediator there are strange loadStates when launch app(1->2->3):
            1. loadState.refresh = Loading
             loadState.mediator = null
             loadState.source.refresh = Loading

            2. loadState.refresh = NotLoading
             loadState.mediator.refresh = NotLoading
             loadState.source.refresh = Loading

            3. loadState.refresh = Loading
             loadState.mediator = Loading
             loadState.source.refresh = Loading

            That 2 loadState brings flicking of progressbar. I found the issue: https://issuetracker.google.com/issues/288023763
            There is no such behavior when use pager without remoteMediator.

            That's why pagingCharacterItems.loadState.append.endOfPaginationReached check was added.
        */
        if (pagingCharacterItems.loadState.refresh is LoadState.Loading
            || (pagingCharacterItems.loadState.refresh is LoadState.NotLoading
                    && pagingCharacterItems.itemCount == 0
                    && !pagingCharacterItems.loadState.append.endOfPaginationReached)
        ) {
            LoadingView(modifier = Modifier.fillMaxSize())
        } else if (pagingCharacterItems.itemCount == 0 && pagingCharacterItems.loadState.append.endOfPaginationReached) {
            EmptyView(modifier = Modifier.fillMaxSize())
        } else {
            val focusManager = LocalFocusManager.current
            val gridState = rememberLazyStaggeredGridState()
            LaunchedEffect(Unit) {//TODO: create custom LazyVerticalGrid with state to clear focus when scroll
                gridState.interactionSource.interactions
                    .distinctUntilChanged()
                    .filterIsInstance<DragInteraction.Start>()
                    .collectLatest {
                        focusManager.clearFocus()
                    }
            }

            LazyVerticalStaggeredGrid(
                state = gridState,
                columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                verticalItemSpacing = PADDING,
                horizontalArrangement = Arrangement.spacedBy(PADDING),
                modifier = Modifier.fillMaxSize()
            ) {
                val stateItemModifier = Modifier.fillMaxWidth()

                items(
                    count = pagingCharacterItems.itemCount,
                    key = pagingCharacterItems.itemKey { character -> character.id },
                ) { index ->
                    pagingCharacterItems[index]?.let { character ->
                        CharacterCard(
                            name = character.name,
                            status = character.status,
                            species = character.species,
                            origin = character.origin.name,
                            imageUrl = character.image,
                            isFavorite = false,
                            onFavoriteClick = {
                            },
                            onCardClick = { onCharacterItemClick(character.id) },
                        )
                    }
                }

                if (pagingCharacterItems.loadState.append is LoadState.Loading) {
                    item(span = FullLine) {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
                if (pagingCharacterItems.loadState.refresh is LoadState.Error) {
                    item(span = FullLine) {
                        ErrorView(
                            error = pagingCharacterItems.loadState.refresh.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }
                if (pagingCharacterItems.loadState.append is LoadState.Error) {
                    item(span = FullLine) {
                        ErrorView(
                            error = pagingCharacterItems.loadState.append.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }

                item(span = FullLine) {}
            }
        }
    }
}

@ThemePreviews
@Composable
fun CharactersListViewPreview() {
    RnmTheme {
        val character1 = Character(
            id = 1,
            name = "Rick Sanchez",
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
        )

        CharactersListView(
            state = CharactersListState(
                characterFilter = CharacterFilter(
                    name = "",
                    status = "",
                    species = "",
                ),
                characters = flowOf(
                    value = PagingData.from(
                        data = listOf(
                            character1,
                            character1.copy(id = 2)
                        ),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false)
                        ),
                        mediatorLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false)
                        )
                    )
                )
            ),
            onSearchChange = {},
            onSearchClear = {},
            onStatusSelected = {},
            onSpeciesSelected = {},
            onGenderSelected = {},
            onTypeSelected = {},
            onCharacterItemClick = {},
            onBackPress = {},
        )
    }
}
