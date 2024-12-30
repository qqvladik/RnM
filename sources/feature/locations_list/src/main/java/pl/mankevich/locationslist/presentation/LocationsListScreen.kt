package pl.mankevich.locationslist.presentation

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Text
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
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListIntent
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListState
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.ui.filter.FilterView
import pl.mankevich.coreui.utils.locationDimensionIconResolver
import pl.mankevich.coreui.utils.locationTypeIconResolver
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.IconButton
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

private val PADDING = 12.dp

@Composable
fun LocationsListScreen(
    viewModel: LocationsListViewModel,
    onLocationItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(sideEffect, onLocationItemClick)
        }
    }

    LocationsListView(
        state = state,
        onSearchChange = { viewModel.sendIntent(LocationsListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(LocationsListIntent.NameChanged("")) },
        onTypeSelected = { viewModel.sendIntent(LocationsListIntent.TypeChanged(it)) },
        onDimensionSelected = { viewModel.sendIntent(LocationsListIntent.DimensionChanged(it)) },
        onCharacterItemClick = { viewModel.sendIntent(LocationsListIntent.CharacterItemClick(it)) },
        onBackPress = onBackPress,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun LocationsListView(
    state: LocationsListState,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onTypeSelected: (String) -> Unit,
    onDimensionSelected: (String) -> Unit,
    onCharacterItemClick: (Int) -> Unit,
    onBackPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val pagingLocationItems = state.locations.collectAsLazyPagingItems()

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(PADDING))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
        ) {
            if (onBackPress != null) {
                IconButton(
                    onClick = onBackPress,
                    imageVector = RnmIcons.CaretLeft,
                    contentDescription = "Show filters",
                    iconSize = 20.dp,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.2f)
                )
            } else {
                Spacer(modifier = Modifier.width(PADDING))
            }

            SearchField(
                value = state.locationFilter.name,
                onValueChange = onSearchChange,
                onClearClick = onSearchClear,
                placeholder = "Search...",
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(PADDING))
        }

        Spacer(modifier = Modifier.height(PADDING))

        FilterView(
            name = "Locations filter",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Type",
                    selected = state.locationFilter.type,
                    labelList = state.typeLabelList,
                    isListFinished = false,
                    resolveIcon = locationTypeIconResolver,
                    onSelectedChanged = onTypeSelected,
                ),
                FilterGroup(
                    name = "Dimension",
                    selected = state.locationFilter.dimension,
                    labelList = state.dimensionLabelList,
                    isListFinished = false,
                    resolveIcon = locationDimensionIconResolver,
                    onSelectedChanged = onDimensionSelected,
                ),

            ),
            scrollablePadding = PADDING,
            modifier = Modifier
                .height(32.dp)
                .padding(end = PADDING)
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

            That's why pagingLocationItems.loadState.append.endOfPaginationReached check was added.
        */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PADDING)
        ) {
            if (pagingLocationItems.loadState.refresh is LoadState.Loading
                || (pagingLocationItems.loadState.refresh is LoadState.NotLoading
                        && pagingLocationItems.itemCount == 0
                        && !pagingLocationItems.loadState.append.endOfPaginationReached)
            ) {
                LoadingView(modifier = Modifier.fillMaxSize())
            } else if (pagingLocationItems.itemCount == 0 && pagingLocationItems.loadState.append.endOfPaginationReached) {
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
                        count = pagingLocationItems.itemCount,
                        key = pagingLocationItems.itemKey { location -> location.id },
                    ) { index ->
                        pagingLocationItems[index]?.let { location ->
                            Text(text=location.toString())
                        }
                    }

                    if (pagingLocationItems.loadState.append is LoadState.Loading) {
                        item(span = FullLine) {
                            LoadingView(
                                modifier = stateItemModifier
                            )
                        }
                    }
                    if (pagingLocationItems.loadState.refresh is LoadState.Error) {
                        item(span = FullLine) {
                            ErrorView(
                                error = pagingLocationItems.loadState.refresh.cast<LoadState.Error>().error,
                                modifier = stateItemModifier
                            ) {
                                pagingLocationItems.retry()
                            }
                        }
                    }
                    if (pagingLocationItems.loadState.append is LoadState.Error) {
                        item(span = FullLine) {
                            ErrorView(
                                error = pagingLocationItems.loadState.append.cast<LoadState.Error>().error,
                                modifier = stateItemModifier
                            ) {
                                pagingLocationItems.retry()
                            }
                        }
                    }

                    item(span = FullLine) {}
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun LocationsListViewPreview() {
    RnmTheme {
        val location1 = Location(
            id = 1,
            name = "Rick Sanchez",
            type = "",
            dimension = "",
        )

        LocationsListView(
            state = LocationsListState(
                locationFilter = LocationFilter(
                    name = "",
                    type = "",
                    dimension = ""
                ),
                locations = flowOf(
                    value = PagingData.from(
                        data = listOf(
                            location1,
                            location1.copy(id = 2)
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
            onTypeSelected = {},
            onDimensionSelected = {},
            onCharacterItemClick = {},
            onBackPress = {},
        )
    }
}
