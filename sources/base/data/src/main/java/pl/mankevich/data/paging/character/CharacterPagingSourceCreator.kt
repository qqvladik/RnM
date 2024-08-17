package pl.mankevich.data.paging.character

import dagger.assisted.AssistedFactory
import pl.mankevich.core.paging.PagingSource
import pl.mankevich.model.Character
import pl.mankevich.model.Filter
import javax.inject.Inject

class CharacterPagingSourceCreator @Inject constructor(
    private val characterPagingSourceCreatorOnline: CharacterPagingSourceCreatorOnline,
    private val characterPagingSourceCreatorOffline: CharacterPagingSourceCreatorOffline
) {

    fun create(isOnline: Boolean, filter: Filter): PagingSource<Character> {
        return if (isOnline) {
            characterPagingSourceCreatorOnline.create(filter)
        } else {
            characterPagingSourceCreatorOffline.create(filter)
        }
    }
}

@AssistedFactory
interface CharacterPagingSourceCreatorOffline {

    fun create(filter: Filter): CharacterPagingSourceOffline
}

@AssistedFactory
interface CharacterPagingSourceCreatorOnline {

    fun create(filter: Filter): CharacterPagingSourceOnline
}