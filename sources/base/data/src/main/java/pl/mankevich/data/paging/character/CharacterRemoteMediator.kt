package pl.mankevich.data.paging.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pl.mankevich.data.mapper.mapToCharacterDto
import pl.mankevich.data.mapper.mapToFilterDto
import pl.mankevich.data.mapper.mapToFilterQuery
import pl.mankevich.model.Character
import pl.mankevich.model.Filter
import pl.mankevich.networkapi.datasource.CharacterNetworkDataSource
import pl.mankevich.storageapi.datasource.CharacterStorageDataSource
import pl.mankevich.storageapi.datasource.Transaction
import pl.mankevich.storageapi.dto.CharacterPageKeyDto

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @AssistedInject constructor( //TODO create base mediator
    private val transaction: Transaction,
    private val characterStorageDataSource: CharacterStorageDataSource,
    private val characterNetworkDataSource: CharacterNetworkDataSource,
    @Assisted private val filter: Filter
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    val remoteKey = state.firstItemOrNull()?.let {
                        characterStorageDataSource.getPageKey(it.id, filter.mapToFilterDto())
                    }
                    remoteKey?.previousPageKey ?: return MediatorResult.Success(remoteKey != null)
                }

                LoadType.APPEND -> {
                    val remoteKey = state.lastItemOrNull()?.let {
                        characterStorageDataSource.getPageKey(it.id, filter.mapToFilterDto())
                    }
                    remoteKey?.nextPageKey ?: return MediatorResult.Success(remoteKey != null)
                }
            }

            val charactersListResponse = characterNetworkDataSource.fetchCharactersList(
                page = currentPage,
                filter = filter.mapToFilterQuery()
            )
            val responseInfo = charactersListResponse.info
            val endOfPaginationReached = responseInfo.next == null

            val prevPage = responseInfo.prev
            val nextPage = responseInfo.next

            val idPageKeys = charactersListResponse.charactersResponse.map {
                CharacterPageKeyDto(
                    characterId = it.id,
                    filter = filter.mapToFilterDto(),
                    value = currentPage,
                    previousPageKey = prevPage,
                    nextPageKey = nextPage
                )
            }
            transaction {
                characterStorageDataSource.insertPageKeysList(idPageKeys)
                characterStorageDataSource.insertCharactersList(characters = charactersListResponse.charactersResponse.map { it.mapToCharacterDto() })
            }

            //TODO add insert list of episodes id
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}