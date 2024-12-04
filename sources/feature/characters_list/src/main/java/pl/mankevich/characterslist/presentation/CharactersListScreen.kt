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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListSideEffect
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.ui.CharacterCard
import pl.mankevich.designsystem.ui.filter.FilterGroup
import pl.mankevich.designsystem.ui.filter.FilterView
import pl.mankevich.designsystem.utils.rememberSaveableMutableStateListOf
import pl.mankevich.model.Filter

private val PADDING = 12.dp

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    onCharacterItemClick: (Int) -> Unit
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    stateWithEffects.sideEffects.forEach { sideEffect ->
        when (sideEffect) {
            is CharactersListSideEffect.OnCharacterItemClicked -> onCharacterItemClick(sideEffect.characterId)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeWithIntents(CharactersListIntent.Refresh(Filter(name = "")))
    }

    val pagingCharacterItems = state.characters.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(horizontal = PADDING)) {
        Spacer(modifier = Modifier.height(PADDING))

        SearchField(
            value = state.filter.name,
            onValueChange = {
                viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(name = it)))
            },
            onClearClick = {
                viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(name = "")))
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
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Pulse },//TODO create icon resolvers
                    onAddLabel = { text -> statusLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(status = it)))
                    },
                ),
                FilterGroup(
                    name = "Species",
                    selected = state.filter.species,
                    labelList = speciesLabelList,
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Alien },//TODO create icon resolvers
                    onAddLabel = { text -> speciesLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(species = it)))
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
                        viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(gender = it)))
                    },
                ),
                FilterGroup(
                    name = "Type",
                    selected = state.filter.type,
                    labelList = typeLabelList,
                    isListFinished = true,
                    resolveIcon = { text -> RnmIcons.GenderIntersex },
                    onAddLabel = { text -> typeLabelList.add(text) },
                    onSelectedChanged = {
                        viewModel.sendIntent(CharactersListIntent.LoadCharacters(Filter(type = it)))
                    },
                )
            ),
            modifier = Modifier.height(32.dp)
        )

        Spacer(modifier = Modifier.height(PADDING))

        if (pagingCharacterItems.loadState.refresh is LoadState.Loading) {
            LoadingView()
        } else if (pagingCharacterItems.itemCount == 0) {
            EmptyView()
        } else {
            LazyVerticalStaggeredGrid( //TODO add savePosition
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = PADDING,
                horizontalArrangement = Arrangement.spacedBy(PADDING),
                modifier = Modifier.fillMaxSize()
            ) {
                val stateItemModifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING)

                if (pagingCharacterItems.loadState.prepend is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.prepend.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }

                if (pagingCharacterItems.loadState.prepend is LoadState.Loading) {
                    item(span = FullLine) {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
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
                    item {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
                if (pagingCharacterItems.loadState.refresh is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.refresh.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }
                if (pagingCharacterItems.loadState.append is LoadState.Error) {
                    item {
                        ErrorView(
                            error = pagingCharacterItems.loadState.append.cast<LoadState.Error>().error,
                            modifier = stateItemModifier
                        ) {
                            pagingCharacterItems.retry()
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(PADDING))
                }
            }
        }
    }
}
