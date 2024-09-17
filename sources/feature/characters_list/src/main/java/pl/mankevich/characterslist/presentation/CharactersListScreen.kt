package pl.mankevich.characterslist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import pl.mankevich.characterslist.presentation.view.SearchField
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListAction
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListSideEffect
import pl.mankevich.characterslist.presentation.viewmodel.CharactersListViewModel
import pl.mankevich.core.util.cast
import pl.mankevich.model.Filter

@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    onCharacterItemClick: (Int) -> Unit
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    stateWithEffects.sideEffects.forEach { sideEffect ->
        when (sideEffect) {
            is CharactersListSideEffect.OnCharacterItemClick -> onCharacterItemClick(sideEffect.characterId)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeWithAction(CharactersListAction.LoadCharacters(true, Filter(name = "")))
    }

    val pagingCharacterItems = state.characters.collectAsLazyPagingItems()

    Column(modifier = Modifier) {
        SearchField(
            value = state.filter.name,
            onValueChange = {
                viewModel.sendAction(CharactersListAction.LoadCharacters(false, Filter(name = it)))
            },
            onClearClick = {
                viewModel.sendAction(
                    CharactersListAction.LoadCharacters(false, Filter(name = ""))
                )
            },
            hint = "Search..."
        )

        if (pagingCharacterItems.loadState.refresh is LoadState.Loading) {
            LoadingView()
        } else if (pagingCharacterItems.itemCount == 0) {
            EmptyView()
        } else {
            LazyColumn( //TODO add savePosition
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val stateItemModifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

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
                    item {
                        LoadingView(
                            modifier = stateItemModifier
                        )
                    }
                }
                items(
                    count = pagingCharacterItems.itemCount,
                    key = pagingCharacterItems.itemKey { character -> character.id }
                ) { index ->
                    pagingCharacterItems[index]?.let { character ->
                        Text(
                            character.id.toString() + " " + character.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                                .clickable {
                                    viewModel.sendAction(
                                        CharactersListAction.CharacterItemClick(
                                            character.id
                                        )
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
            }
        }
    }
}
