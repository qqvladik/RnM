package pl.mankevich.locationslist.presentation

import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import pl.mankevich.core.util.cast
import pl.mankevich.coreui.ui.LocationCard
import pl.mankevich.coreui.ui.LocationCardPlaceholder
import pl.mankevich.coreui.ui.SearchFilterAppBar
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.utils.locationDimensionIconResolver
import pl.mankevich.coreui.utils.locationTypeIconResolver
import pl.mankevich.designsystem.component.EmptyView
import pl.mankevich.designsystem.component.ErrorView
import pl.mankevich.designsystem.component.FlexibleTopBar
import pl.mankevich.designsystem.component.LoadingView
import pl.mankevich.designsystem.component.expandAnimating
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.CurrentTabClickHandler
import pl.mankevich.designsystem.utils.ObserveGridStateToClearFocus
import pl.mankevich.designsystem.utils.WithAnimatedVisibilityScope
import pl.mankevich.designsystem.utils.WithSharedTransitionScope
import pl.mankevich.designsystem.utils.isLandscape
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListIntent
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListSideEffect
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListState
import pl.mankevich.locationslist.presentation.viewmodel.LocationsListViewModel
import pl.mankevich.model.Location
import pl.mankevich.model.LocationFilter

@Composable
fun LocationsListScreen(
    viewModel: LocationsListViewModel,
    navigateToLocationDetail: (Int) -> Unit,
    navigateBack: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            when (sideEffect) {
                is LocationsListSideEffect.NavigateToLocationDetail ->
                    navigateToLocationDetail(sideEffect.locationId)

                is LocationsListSideEffect.NavigateBack ->
                    navigateBack?.invoke()
            }
        }
    }

    LocationsListView(
        state = state,
        onRefresh = { viewModel.sendIntent(LocationsListIntent.Refresh) },
        onSearchChange = { viewModel.sendIntent(LocationsListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(LocationsListIntent.NameChanged("")) },
        onTypeSelected = { viewModel.sendIntent(LocationsListIntent.TypeChanged(it)) },
        onDimensionSelected = { viewModel.sendIntent(LocationsListIntent.DimensionChanged(it)) },
        onLocationItemClick = { viewModel.sendIntent(LocationsListIntent.LocationItemClick(it)) },
        onBackClick = navigateBack?.let { { viewModel.sendIntent(LocationsListIntent.BackClick) } },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun LocationsListView(
    modifier: Modifier = Modifier,
    state: LocationsListState,
    onRefresh: () -> Unit,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onTypeSelected: (String) -> Unit,
    onDimensionSelected: (String) -> Unit,
    onLocationItemClick: (Int) -> Unit,
    onBackClick: (() -> Unit)? = null,
) {
    val pagingLocationItems = state.locations.collectAsLazyPagingItems()

    val isLoading = pagingLocationItems.loadState.refresh is LoadState.Loading
            || (pagingLocationItems.loadState.refresh is LoadState.NotLoading
            && pagingLocationItems.itemCount == 0
            && !pagingLocationItems.loadState.append.endOfPaginationReached)
    val isSuccess = !isLoading && pagingLocationItems.loadState.refresh is LoadState.NotLoading
    val isEmpty = isSuccess
            && pagingLocationItems.itemCount == 0
            && pagingLocationItems.loadState.append.endOfPaginationReached

    val isOffline = !state.isOnline

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(isOffline, isSuccess) {
        if (isOffline && isSuccess) {
            snackbarHostState.showSnackbar(
                message = "No internet. Showing cached data. Refresh for updates.",
                duration = Indefinite,
            )
        }
    }

    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                    )
                },
                contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    .add(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)), // For snackbar
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    FlexibleTopBar(
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                                .animateEnterExit(
                                    enter = slideInVertically { -it },
                                    exit = slideOutVertically { -it }
                                )
                                .background(color = MaterialTheme.colorScheme.background)
                        ) {
                            SearchFilterAppBar(
                                searchValue = state.locationFilter.name,
                                onSearchChange = onSearchChange,
                                onSearchClear = onSearchClear,
                                filterName = "Locations filter",
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
                                onBackPress = onBackClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsPadding(
                                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
                                    )
                            )
                        }
                    }
                },
                content = { paddingValues ->
                    val infiniteTransition =
                        rememberInfiniteTransition(label = "LocationsListScreen transition")

                    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

                    CurrentTabClickHandler {
                        lazyStaggeredGridState.animateScrollToItem(0)
                        scrollBehavior.expandAnimating()
                    }

                    ObserveGridStateToClearFocus(
                        lazyStaggeredGridState = lazyStaggeredGridState,
                    )

                    // Show refreshing indicator at the same time as items placeholder only when refresh is triggered by pull-to-refresh
                    // Workaround to handle isRefreshing state in view layer, because Paging 3 doesn't lay good in MVI.
                    var isRefreshing by rememberSaveable { mutableStateOf(false) }
                    LaunchedEffect(isLoading) {
                        if (!isLoading) {
                            isRefreshing = false
                        }
                    }

                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true
                            onRefresh()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                            .padding(top = paddingValues.calculateTopPadding()),
                    ) {

                        LazyVerticalStaggeredGrid(
                            state = lazyStaggeredGridState,
                            columns = StaggeredGridCells.Fixed(if (isLandscape()) 3 else 2),
                            verticalItemSpacing = PADDING,
                            horizontalArrangement = Arrangement.spacedBy(PADDING),
                            contentPadding = PaddingValues(
                                bottom = paddingValues.calculateBottomPadding() + PADDING,
                                start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + PADDING,
                                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + PADDING
                            ),
                            modifier = Modifier.fillMaxSize()
                        ) {

                            val itemModifier = Modifier
                                .height(135.dp)
                                .fillMaxWidth()

                            if (isLoading) {
                                items(20) {
                                    LocationCardPlaceholder(
                                        infiniteTransition = infiniteTransition,
                                        modifier = itemModifier
                                    )
                                }
                            } else {
                                if (isEmpty) {
                                    item(span = FullLine) {
                                        EmptyView(modifier = Modifier.fillMaxSize())
                                    }
                                } else {
                                    locationsListViewData(
                                        pagingLocationItems = pagingLocationItems,
                                        onLocationItemClick = onLocationItemClick,
                                        itemModifier = itemModifier
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

fun LazyStaggeredGridScope.locationsListViewData(
    pagingLocationItems: LazyPagingItems<Location>,
    onLocationItemClick: (Int) -> Unit,
    itemModifier: Modifier
) {
    items(
        count = pagingLocationItems.itemCount,
        key = pagingLocationItems.itemKey { location -> location.id },
    ) { index ->
        pagingLocationItems[index]?.let { location ->
            LocationCard(
                id = location.id,
                type = location.type,
                name = location.name,
                icon = locationTypeIconResolver(location.type),
                isFavorite = false,
                onFavoriteClick = {},
                onLocationClick = { onLocationItemClick(location.id) },
                modifier = itemModifier
            )
        }
    }

    val stateItemModifier = Modifier.fillMaxWidth()

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
}

@ThemePreviews
@Composable
fun LocationsListViewPreview() {
    RnmTheme {
        val location1 = Location(
            id = 1,
            name = "Earth (C-137)",
            type = "Planet",
            dimension = "Dimension C-137",
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
            onRefresh = {},
            onSearchChange = {},
            onSearchClear = {},
            onTypeSelected = {},
            onDimensionSelected = {},
            onLocationItemClick = {},
            onBackClick = {},
        )
    }
}
