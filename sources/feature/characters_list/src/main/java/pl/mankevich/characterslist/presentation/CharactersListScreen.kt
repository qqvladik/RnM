package pl.mankevich.characterslist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import pl.mankevich.characterslist.presentation.view.EmptyView
import pl.mankevich.characterslist.presentation.view.ErrorView
import pl.mankevich.characterslist.presentation.view.LoadingView
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListIntent
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.ui.CharacterCard
import pl.mankevich.designsystem.ui.filter.FilterGroup
import pl.mankevich.designsystem.ui.filter.FilterView
import pl.mankevich.designsystem.utils.rememberSaveableMutableStateListOf

private val PADDING = 12.dp

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    onCharacterItemClick: (Int) -> Unit
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach { sideEffect ->
            viewModel.handleSideEffect(sideEffect, onCharacterItemClick)
        }
    }

    val pagingCharacterItems = state.characters.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(horizontal = PADDING)) {
        Spacer(modifier = Modifier.height(PADDING))

        SearchField(
            value = state.filter.name,
            onValueChange = {
                viewModel.sendIntent(CharactersListIntent.NameFilterChanged(it))
            },
            onClearClick = {
                viewModel.sendIntent(CharactersListIntent.NameFilterChanged(""))
            },
            placeholder = "Search..."
        )

        Spacer(modifier = Modifier.height(PADDING))

        val statusLabelList = rememberSaveableMutableStateListOf(
            "Alive", "Dead", "Unknown"
        )
        val speciesLabelList = rememberSaveableMutableStateListOf(
            "Alien", "Human", "Humanoid", "Robo"
        )
        val genderLabelList = rememberSaveableMutableStateListOf(
            "Male", "Female", "Genderless", "Unknown"
        )
        val typeLabelList = rememberSaveableMutableStateListOf(
            "Parasite"
        )

        FilterView(
            name = "Characters filter",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Status",
                    selected = state.filter.status,
                    labelList = statusLabelList,
                    isListFinished = true,
                    resolveIcon = { text -> RnmIcons.Pulse },
                    onAddLabel = { text -> statusLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.StatusFilterChanged(it))
                    },
                ),
                FilterGroup(
                    name = "Species",
                    selected = state.filter.species,
                    labelList = speciesLabelList,
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Alien },
                    onAddLabel = { text -> speciesLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.SpeciesFilterChanged(it))
                    },
                ),
                FilterGroup(
                    name = "Gender",
                    selected = state.filter.gender,
                    labelList = genderLabelList,
                    isListFinished = true,
                    resolveIcon = { text -> RnmIcons.GenderIntersex },
                    onAddLabel = { text -> genderLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.GenderFilterChanged(it))
                    },
                ),
                FilterGroup(
                    name = "Type",
                    selected = state.filter.type,
                    labelList = typeLabelList,
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Blocks },
                    onAddLabel = { text -> typeLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.TypeFilterChanged(it))
                    },
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
            || (pagingCharacterItems.loadState.refresh !is LoadState.Loading
                    && pagingCharacterItems.itemCount == 0
                    && !pagingCharacterItems.loadState.append.endOfPaginationReached)
        ) {
            LoadingView()
        } else if (pagingCharacterItems.itemCount == 0 && pagingCharacterItems.loadState.append.endOfPaginationReached) {
            EmptyView()
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = PADDING,
                horizontalArrangement = Arrangement.spacedBy(PADDING),
                modifier = Modifier.fillMaxSize()
            ) {
                val stateItemModifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING)

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
                            onCardClick = {
                                viewModel.sendIntent(
                                    CharactersListIntent.CharacterItemClick(character.id)
                                )
                            }
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
