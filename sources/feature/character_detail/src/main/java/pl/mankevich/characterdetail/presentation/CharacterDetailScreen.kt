package pl.mankevich.characterdetail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    viewModel: CharacterDetailViewModel
) {
    Text("CharacterId = $characterId")
}
