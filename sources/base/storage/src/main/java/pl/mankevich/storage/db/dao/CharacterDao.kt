package pl.mankevich.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.mankevich.storage.db.entity.CharacterEntity
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.CHARACTER_TABLE_NAME
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.GENDER_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.ID_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.LOCATION_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.NAME_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.ORIGIN_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.SPECIES_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.STATUS_COLUMN
import pl.mankevich.storage.db.entity.CharacterEntity.Companion.TYPE_COLUMN
import pl.mankevich.storage.db.entity.LocationEmbedded

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharactersList(characters: List<CharacterEntity>)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $ID_COLUMN=(:id)")//todo check if we need null
    suspend fun getCharacterById(id: Int): CharacterEntity?

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
        limit: Int = 20,
        offset: Int = 0 //TODO check
    ): List<CharacterEntity>
}