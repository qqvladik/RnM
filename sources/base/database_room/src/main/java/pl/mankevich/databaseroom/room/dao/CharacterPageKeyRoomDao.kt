package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity
import pl.mankevich.databaseapi.entity.CharacterFilterEntity
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.CHARACTER_ID_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterPageKeyRoomEntity.Companion.CHARACTER_PAGE_KEY_TABLE_NAME

@Dao
interface CharacterPageKeyRoomDao {

    @Query("SELECT * FROM $CHARACTER_PAGE_KEY_TABLE_NAME WHERE $CHARACTER_ID_COLUMN =:characterId AND filter=:filter")
    suspend fun getPageKey(characterId: Int, filter: CharacterFilterEntity): CharacterPageKeyRoomEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPageKeysList(pageKeysList: List<CharacterPageKeyRoomEntity>)

    @Query("DELETE FROM $CHARACTER_PAGE_KEY_TABLE_NAME")
    suspend fun deleteAllPageKeys()

    @Query(
        "SELECT $CHARACTER_ID_COLUMN FROM $CHARACTER_PAGE_KEY_TABLE_NAME WHERE filter = :filter " +
                "ORDER BY $CHARACTER_ID_COLUMN ASC LIMIT :limit OFFSET :offset"
    )
    suspend fun getCharacterIdsByFilter(
        filter: CharacterFilterEntity,
        limit: Int,
        offset: Int
    ): List<Int>

    @Query("SELECT COUNT(*) FROM $CHARACTER_PAGE_KEY_TABLE_NAME WHERE filter=:filter")
    suspend fun getCount(filter: CharacterFilterEntity): Int
}