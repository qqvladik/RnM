package pl.mankevich.data.paging.character

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.data.mapper.mapToCharacterDto
import pl.mankevich.data.mapper.mapToEntity
import pl.mankevich.data.mapper.mapToQuery
import pl.mankevich.model.Character
import pl.mankevich.model.CharacterFilter
import pl.mankevich.remoteapi.api.CharacterApi
import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction
import pl.mankevich.databaseapi.entity.CharacterPageKeyEntity

class CharacterRemoteMediator @AssistedInject constructor( //TODO create base mediator
    private val transaction: Transaction,
    private val characterDao: CharacterDao,
    private val relationsDao: RelationsDao,
    private val characterApi: CharacterApi,
    @Assisted private val characterFilter: CharacterFilter
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    1
                    // It works bad when remoteKey > 1. It scrolls to the top and loads PREPEND data
                    // infinitely because Room's PagingSource has such weird behavior
//                    val remoteKey = state.anchorPosition?.let { position ->
//                        state.closestItemToPosition(position)?.let {
//                            characterDao.getPageKey(it.id, filter.mapToFilterDto())
//                        }
//                    }
//                    remoteKey?.value ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = state.firstItemOrNull()?.let {
                        characterDao.getPageKey(it.id, characterFilter.mapToEntity())
                    }
                    remoteKey?.previousPageKey ?: return MediatorResult.Success(remoteKey != null)
                }

                LoadType.APPEND -> {
                    val remoteKey = state.lastItemOrNull()?.let {
                        characterDao.getPageKey(it.id, characterFilter.mapToEntity())
                    }
                    remoteKey?.nextPageKey ?: return MediatorResult.Success(remoteKey != null)
                }
            }

            val charactersListResponse = characterApi.fetchCharactersList(
                page = currentPage,
                filter = characterFilter.mapToQuery()
            )
            val responseInfo = charactersListResponse.info

            val idPageKeys = charactersListResponse.charactersResponse.map {
                CharacterPageKeyEntity(
                    characterId = it.id,
                    filter = characterFilter.mapToEntity(),
                    value = currentPage,
                    previousPageKey = responseInfo.prev,
                    nextPageKey = responseInfo.next
                )
            }

            transaction {
                val characters = charactersListResponse.charactersResponse.map { characterResponse ->
                    relationsDao.insertCharacterEpisodes(characterResponse.id, characterResponse.episodeIds)
                    characterResponse.mapToCharacterDto()
                }

                characterDao.insertPageKeysList(idPageKeys)
                characterDao.insertCharactersList(characters)
            }
            MediatorResult.Success(endOfPaginationReached = responseInfo.next == null)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}