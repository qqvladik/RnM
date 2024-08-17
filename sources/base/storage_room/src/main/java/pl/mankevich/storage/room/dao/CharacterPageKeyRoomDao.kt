package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.storage.room.entity.CharacterPageKeyEntity
import pl.mankevich.storageapi.dto.FilterDto

@Dao
interface CharacterPageKeyRoomDao {

    @Query("SELECT * FROM CharacterPageKeyEntity WHERE characterId =:characterId AND filter=:filter")
    suspend fun getPageKey(characterId: Int, filter: FilterDto): CharacterPageKeyEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPageKeysList(pageKeysList: List<CharacterPageKeyEntity>)

    @Query("DELETE FROM CharacterPageKeyEntity")
    suspend fun deleteAllPageKeys()

    @Query(
        "SELECT characterId FROM CharacterPageKeyEntity WHERE filter = :filter " +
                "ORDER BY characterId ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getCharacterIdsByFilter(
        filter: FilterDto,
        limit: Int,
        offset: Int
    ): List<Int>

    @Query("SELECT COUNT(*) FROM CharacterPageKeyEntity WHERE filter=:filter")
    suspend fun getCount(filter: FilterDto): Int
}