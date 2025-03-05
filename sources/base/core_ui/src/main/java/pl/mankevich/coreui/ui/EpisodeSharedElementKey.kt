package pl.mankevich.coreui.ui

data class EpisodeSharedElementKey(
    val id: Int,
    val sharedType: EpisodeSharedElementType,
)

enum class EpisodeSharedElementType {
    Background,
    Name,
    Season,
    Episode,
}
