package pl.mankevich.characterdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    SideEffect {
        stateWithEffects.sideEffects.forEach {
            viewModel.handleSideEffect(it)
        }
    }

    Column {
        Text("character = ${state.character}")
        Text("episodes = ${state.episodes}")
    }

}
