package pl.mankevich.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mankevich.storage.room.entity.CharacterEntity
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.CHARACTER_TABLE_NAME
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.GENDER_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.ID_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.LOCATION_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.NAME_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.ORIGIN_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.SPECIES_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.STATUS_COLUMN
import pl.mankevich.storage.room.entity.CharacterEntity.Companion.TYPE_COLUMN
import pl.mankevich.storage.room.entity.LocationEmbedded

@Dao
interface CharacterRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersList(characters: List<CharacterEntity>)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN=(:id)")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN in (:ids)")
    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterEntity>

    @Query(
        "SELECT * FROM $CHARACTER_TABLE_NAME " +//todo add to lower case
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
    ): List<CharacterEntity>

    @Query(
        "SELECT COUNT(*) FROM ( $CHARACTER_TABLE_NAME )" +//todo add to lower case
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