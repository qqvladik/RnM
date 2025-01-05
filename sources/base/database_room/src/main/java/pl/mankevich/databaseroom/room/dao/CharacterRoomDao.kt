package pl.mankevich.databaseroom.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.CHARACTER_TABLE_NAME
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.GENDER_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.ID_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.LOCATION_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.NAME_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.ORIGIN_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.SPECIES_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.STATUS_COLUMN
import pl.mankevich.databaseroom.room.entity.CharacterRoomEntity.Companion.TYPE_COLUMN
import pl.mankevich.databaseroom.room.entity.LocationEmbedded

@Dao
interface CharacterRoomDao {

    @Upsert
    suspend fun insertCharacter(character: CharacterRoomEntity)

    @Upsert
    suspend fun insertCharactersList(characters: List<CharacterRoomEntity>)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    fun getCharacterById(id: Int): Flow<CharacterRoomEntity>

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterRoomEntity>

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    fun getCharactersFlowByIds(ids: List<Int>): Flow<List<CharacterRoomEntity>>

    @Query(
        "SELECT * FROM $CHARACTER_TABLE_NAME " +
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $STATUS_COLUMN LIKE '%' || (:status) || '%' " +
                "AND $SPECIES_COLUMN LIKE '%' || (:species) || '%' " +
                "AND $TYPE_COLUMN LIKE '%' || (:type) || '%' " +
                "AND $GENDER_COLUMN LIKE '%' || (:gender) || '%' " +
                "AND ${
                    ORIGIN_COLUMN.plus("_").plus(LocationEmbedded.NAME_COLUMN)
                } LIKE '%' || (:origin) || '%' " +
                "AND ${
                    LOCATION_COLUMN.plus("_").plus(LocationEmbedded.NAME_COLUMN)
                } LIKE '%' || (:location) || '%'" +
                "ORDER BY $ID_COLUMN ASC LIMIT (:limit) OFFSET (:offset)"
    )
    suspend fun getCharactersList(
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = "",
        origin: String = "",
        location: String = "",
        limit: Int,
        offset: Int
    ): List<CharacterRoomEntity>

    @Query(
        "SELECT COUNT(*) FROM ( $CHARACTER_TABLE_NAME )" +
                "WHERE $NAME_COLUMN LIKE '%' || (:name) || '%' " +
                "AND $STATUS_COLUMN LIKE '%' || (:status) || '%' " +
                "AND $SPECIES_COLUMN LIKE '%' || (:species) || '%' " +
                "AND $TYPE_COLUMN LIKE '%' || (:type) || '%' " +
                "AND $GENDER_COLUMN LIKE '%' || (:gender) || '%' "
    )
    suspend fun getCount(
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = "",
    ): Int
}