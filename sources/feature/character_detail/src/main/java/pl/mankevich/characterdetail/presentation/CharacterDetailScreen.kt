package pl.mankevich.characterdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailAction
import pl.mankevich.characterdetail.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    viewModel: CharacterDetailViewModel
) {
    val stateWithEffects by viewModel.stateWithEffects.collectAsStateWithLifecycle()
    val state = stateWithEffects.state

    LaunchedEffect(Unit) {
        viewModel.initializeWithActions(CharacterDetailAction.LoadCharacter, CharacterDetailAction.LoadEpisodes) //TODO add viewEvents, because now ui knows about not related actions, such as LoadEpisodesSuccess
    }

    Column {
        Text("characterId = $characterId")
        val character by state.character.collectAsStateWithLifecycle(initialValue = null)  //TODO remove initialValue when add stateIn in viewModel
        Text("character = $character")
        val episodes by state.episodes.collectAsStateWithLifecycle(initialValue = null)
        Text("episodes = $episodes")
    }

}
