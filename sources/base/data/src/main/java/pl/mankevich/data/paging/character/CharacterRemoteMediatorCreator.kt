package pl.mankevich.data.paging.character

import dagger.assisted.AssistedFactory
import pl.mankevich.model.CharacterFilter
import javax.inject.Inject

class CharacterRemoteMediatorCreator @Inject constructor(
    private val characterRemoteMediatorFactoryOnline: CharacterRemoteMediatorFactoryOnline
) {

    fun create(isOnline: Boolean, characterFilter: CharacterFilter): CharacterRemoteMediator? {
        return if (isOnline) {
            characterRemoteMediatorFactoryOnline.create(characterFilter)
        } else {
            null
        }
    }
}

@AssistedFactory
interface CharacterRemoteMediatorFactoryOnline {

    fun create(characterFilter: CharacterFilter): CharacterRemoteMediator
}