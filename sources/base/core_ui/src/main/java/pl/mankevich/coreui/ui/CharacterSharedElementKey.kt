package pl.mankevich.coreui.ui

data class CharacterSharedElementKey(
    val id: Int,
    val sharedType: CharacterSharedElementType,
)

enum class CharacterSharedElementType {
    Background,
    Name,
    Image,
    Status,
    Species,
    Origin,
}
