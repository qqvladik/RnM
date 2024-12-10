package pl.mankevich.data.paging.character

import dagger.assisted.AssistedFactory
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import javax.inject.Inject

class CharacterPagingSourceCreator @Inject constructor(
    private val characterPagingSourceCreatorOnline: CharacterPagingSourceCreatorOnline,
    private val characterPagingSourceCreatorOffline: CharacterPagingSourceCreatorOffline
) {

    fun create(isOnline: Boolean, characterFilter: CharacterFilter): PagingSource<Character> {
        return if (isOnline) {
            characterPagingSourceCreatorOnline.create(characterFilter)
        } else {
            characterPagingSourceCreatorOffline.create(characterFilter)
        }
    }
}

@AssistedFactory
interface CharacterPagingSourceCreatorOffline {

    fun create(characterFilter: CharacterFilter): CharacterPagingSourceOffline
}

@AssistedFactory
interface CharacterPagingSourceCreatorOnline {

    fun create(characterFilter: CharacterFilter): CharacterPagingSourceOnline
}