package pl.mankevich.coreui.ui

data class LocationSharedElementKey(
    val id: Int?,
    val sharedType: LocationSharedElementType,
)

enum class LocationSharedElementType {
    Background,
    Name,
    Type,
    Icon,
}
