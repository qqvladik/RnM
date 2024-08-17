package pl.mankevich.storage.room.entity

import androidx.room.Entity
import pl.mankevich.storageapi.dto.FilterDto

@Entity(primaryKeys = ["characterId", "filter", "value"])
data class CharacterPageKeyEntity(
    val characterId: Int,
    val filter: FilterDto,
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null
)