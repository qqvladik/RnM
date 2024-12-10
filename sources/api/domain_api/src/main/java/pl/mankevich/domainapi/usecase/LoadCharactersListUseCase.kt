package pl.mankevich.domainapi.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter

interface LoadCharactersListUseCase {

    suspend operator fun invoke(characterFilter: CharacterFilter): Flow<PagingData<Character>>
}