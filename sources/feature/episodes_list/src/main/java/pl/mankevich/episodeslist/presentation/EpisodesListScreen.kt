package pl.mankevich.episodeslist.presentation

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
import pl.mankevich.coreui.ui.EpisodeCard
import pl.mankevich.coreui.ui.EpisodeCardPlaceholder
import pl.mankevich.coreui.ui.SearchFilterAppBar
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.utils.episodeEpisodeIconResolver
import pl.mankevich.coreui.utils.episodeSeasonIconResolver
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
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListIntent
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListSideEffect
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListState
import pl.mankevich.episodeslist.presentation.viewmodel.EpisodesListViewModel
import pl.mankevich.model.Episode
import pl.mankevich.model.EpisodeFilter

@Composable
fun EpisodesListScreen(
    viewModel: EpisodesListViewModel,
    navigateToEpisodeDetail: (Int) -> Unit,
    navigateBack: (() -> Unit)? = null,
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            when (sideEffect) {
                is EpisodesListSideEffect.NavigateToEpisodeDetail ->
                    navigateToEpisodeDetail(sideEffect.episodeId)

                EpisodesListSideEffect.NavigateBack ->
                    navigateBack?.invoke()
            }
        }
    }

    EpisodesListView(
        state = state,
        onRefresh = { viewModel.sendIntent(EpisodesListIntent.Refresh) },
        onSearchChange = { viewModel.sendIntent(EpisodesListIntent.NameChanged(it)) },
        onSearchClear = { viewModel.sendIntent(EpisodesListIntent.NameChanged("")) },
        onSeasonSelected = { viewModel.sendIntent(EpisodesListIntent.SeasonChanged(it.toIntOrNull())) },
        onEpisodeSelected = { viewModel.sendIntent(EpisodesListIntent.EpisodeChanged(it.toIntOrNull())) },
        onEpisodeItemClick = { viewModel.sendIntent(EpisodesListIntent.EpisodeItemClick(it)) },
        onBackClick = navigateBack?.let { { viewModel.sendIntent(EpisodesListIntent.BackClick) } },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun EpisodesListView(
    modifier: Modifier = Modifier,
    state: EpisodesListState,
    onRefresh: () -> Unit,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onSeasonSelected: (String) -> Unit,
    onEpisodeSelected: (String) -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onBackClick: (() -> Unit)? = null,
) {
    val pagingEpisodeItems = state.episodes.collectAsLazyPagingItems()
    val isOffline = !state.isOnline

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(isOffline) {
        if (isOffline) {
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
                                searchValue = state.episodeFilter.name,
                                onSearchChange = onSearchChange,
                                onSearchClear = onSearchClear,
                                filterName = "Locations filter",
                                filterGroupList = listOf(
                                    FilterGroup(
                                        name = "Season",
                                        selected = state.episodeFilter.season.toString(),
                                        labelList = state.seasonLabelList,
                                        isListFinished = true,
                                        resolveIcon = episodeSeasonIconResolver,
                                        onSelectedChanged = onSeasonSelected,
                                    ),
                                    FilterGroup(
                                        name = "Episode",
                                        selected = state.episodeFilter.episode.toString(),
                                        labelList = state.episodeLabelList,
                                        isListFinished = false,
                                        resolveIcon = episodeEpisodeIconResolver,
                                        onSelectedChanged = onEpisodeSelected,
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
                    LaunchedEffect(pagingEpisodeItems.loadState.refresh) {
                        if (pagingEpisodeItems.loadState.refresh is LoadState.NotLoading) {
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
                                .height(80.dp)
                                .fillMaxWidth()

                            if (pagingEpisodeItems.loadState.refresh is LoadState.Loading
                                || (pagingEpisodeItems.loadState.refresh is LoadState.NotLoading
                                        && pagingEpisodeItems.itemCount == 0
                                        && !pagingEpisodeItems.loadState.append.endOfPaginationReached)
                            ) {
                                items(20) {
                                    EpisodeCardPlaceholder(
                                        infiniteTransition = infiniteTransition,
                                        modifier = itemModifier
                                    )
                                }
                            } else if (pagingEpisodeItems.itemCount == 0 && pagingEpisodeItems.loadState.append.endOfPaginationReached) {
                                item(span = FullLine) {
                                    EmptyView(modifier = Modifier.fillMaxSize())
                                }
                            } else {
                                episodeListViewData(
                                    pagingEpisodeItems = pagingEpisodeItems,
                                    onEpisodeItemClick = onEpisodeItemClick,
                                    itemModifier = itemModifier
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

fun LazyStaggeredGridScope.episodeListViewData(
    pagingEpisodeItems: LazyPagingItems<Episode>,
    onEpisodeItemClick: (Int) -> Unit,
    itemModifier: Modifier
) {
    items(
        count = pagingEpisodeItems.itemCount,
        key = pagingEpisodeItems.itemKey { location -> location.id },
    ) { index ->
        pagingEpisodeItems[index]?.let { episode ->
            EpisodeCard(
                id = episode.id,
                name = episode.name,
                season = episode.season.toString(),
                episode = episode.episode.toString(),
                isFavorite = false,
                onFavoriteClick = {},
                onCardClick = {
                    onEpisodeItemClick(episode.id)
                },
                modifier = itemModifier
            )
        }
    }

    val stateItemModifier = Modifier.fillMaxWidth()

    if (pagingEpisodeItems.loadState.append is LoadState.Loading) {
        item(span = FullLine) {
            LoadingView(
                modifier = stateItemModifier
            )
        }
    }
    if (pagingEpisodeItems.loadState.refresh is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingEpisodeItems.loadState.refresh.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingEpisodeItems.retry()
            }
        }
    }
    if (pagingEpisodeItems.loadState.append is LoadState.Error) {
        item(span = FullLine) {
            ErrorView(
                error = pagingEpisodeItems.loadState.append.cast<LoadState.Error>().error,
                modifier = stateItemModifier
            ) {
                pagingEpisodeItems.retry()
            }
        }
    }
}

@ThemePreviews
@Composable
fun EpisodesListViewPreview() {
    RnmTheme {
        val episode1 = Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 9, 2013",
            season = 1,
            episode = 2,
        )

        EpisodesListView(
            state = EpisodesListState(
                episodeFilter = EpisodeFilter(
                    name = "",
                    episode = null,
                    season = null,
                ),
                episodes = flowOf(
                    value = PagingData.from(
                        data = listOf(
                            episode1,
                            episode1.copy(id = 2)
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
            onEpisodeSelected = {},
            onSeasonSelected = {},
            onEpisodeItemClick = {},
            onBackClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
