package pl.mankevich.data.paging.character

import dagger.assisted.AssistedFactory
import pl.mankevich.model.Filter
import javax.inject.Inject

class CharacterRemoteMediatorCreator @Inject constructor(
    private val characterRemoteMediatorFactoryOnline: CharacterRemoteMediatorFactoryOnline
) {

    fun create(isOnline: Boolean, filter: Filter): CharacterRemoteMediator? {
        return if (isOnline) {
            characterRemoteMediatorFactoryOnline.create(filter)
        } else {
            null
        }
    }
}

@AssistedFactory
interface CharacterRemoteMediatorFactoryOnline {

    fun create(filter: Filter): CharacterRemoteMediator
}